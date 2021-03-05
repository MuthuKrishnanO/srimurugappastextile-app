package com.techface.srimurugapa.scheme.Helper;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;


import com.techface.srimurugapa.scheme.R;

import java.io.Serializable;
import java.util.List;

/*import com.itextpdf.text.Font;*/
public class SearchableListDialog extends DialogFragment implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener {


    private static final String ITEMS = "items";

    private ListAdapter listAdapter;

    private ListView _listViewItems;

    public SearchableItem _searchableItem;

    private OnSearchTextChanged _onSearchTextChanged;

    private SearchView _searchView;

    private String _strTitle;

    private String _strTitle1;

    private String _strPositiveButtonText;

    private DialogInterface.OnClickListener _onClickListener;

    public SearchableListDialog() {

    }


    public static SearchableListDialog newInstance(List items) {
        SearchableListDialog multiSelectExpandableFragment = new
                SearchableListDialog();

        Bundle args = new Bundle();
        args.putSerializable(ITEMS, (Serializable) items);

        multiSelectExpandableFragment.setArguments(args);

        return multiSelectExpandableFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Getting the layout inflater to inflate the view in an alert dialog.
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        // Crash on orientation change #7
        // Change Start
        // Description: As the instance was re initializing to null on rotating the device,
        // getting the instance from the saved instance
        if (null != savedInstanceState) {
            _searchableItem = (SearchableItem) savedInstanceState.getSerializable("item");
        }
        // Change End

        View rootView = inflater.inflate(R.layout.searchable_list_dialog, null);
        setData(rootView);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setView(rootView);

        String strPositiveButton = _strPositiveButtonText == null ? "CLOSE" : _strPositiveButtonText;
        alertDialog.setPositiveButton(strPositiveButton, _onClickListener);

        TextView title = new TextView(getActivity());
        String strTitle = _strTitle == null ? "Select Item" : _strTitle;
        title.setText(strTitle);
        title.setTextColor(getResources().getColor(R.color.colorAccent));
        title.setTextSize(20);
        title.setPadding(0, 70, 0, 0);
        title.setGravity(Gravity.CENTER);
   /*     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            title.setTextAppearance(R.style.edit_Text_Style);
        }*/
        alertDialog.setCustomTitle(title);


        final AlertDialog dialog = alertDialog.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return dialog;
    }

    // Crash on orientation change #7
    // Change Start
    // Description: Saving the instance of searchable item instance.
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("item", _searchableItem);
        super.onSaveInstanceState(outState);
    }
    // Change End

    public void setTitle(String strTitle) {
        _strTitle = strTitle;
    }


    public void setPositiveButton(String strPositiveButtonText) {
        _strPositiveButtonText = strPositiveButtonText;
    }

    public void setPositiveButton(String strPositiveButtonText, DialogInterface.OnClickListener onClickListener) {

        TextView title1 = new TextView(getActivity());
        _strPositiveButtonText = strPositiveButtonText;
        _onClickListener = onClickListener;

    }


    public void setOnSearchableItemClickListener(SearchableItem searchableItem) {
        this._searchableItem = searchableItem;
    }


    public void setOnSearchTextChangedListener(OnSearchTextChanged onSearchTextChanged) {
        this._onSearchTextChanged = onSearchTextChanged;
    }

    private void setData(View rootView) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context
                .SEARCH_SERVICE);

        _searchView = (SearchView) rootView.findViewById(R.id.search);

        AutoCompleteTextView search_text = (AutoCompleteTextView) _searchView.findViewById(_searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null));
        //   search_text.setTextColor(Color.WHITE);
        search_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.fab_margin));

        _searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName
                ()));
        _searchView.setIconifiedByDefault(false);
        _searchView.setOnQueryTextListener(this);
        _searchView.setOnCloseListener(this);
        _searchView.clearFocus();

        _searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context
                            .INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(_searchView.getWindowToken(), 0);
                }
            }
        });


        List items = (List) getArguments().getSerializable(ITEMS);

        _listViewItems = (ListView) rootView.findViewById(R.id.listItems);

        //create the adapter by passing your ArrayList data
        listAdapter = new ListAdapter(items);
        //attach the adapter to the list
        _listViewItems.setAdapter(listAdapter);

        _listViewItems.setTextFilterEnabled(true);

        _listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _searchableItem.onSearchableItemClicked(listAdapter.getItem(position), position);
                getDialog().dismiss();
            }
        });
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        _searchView.clearFocus();
        return true;
    }


    @Override
    public boolean onQueryTextChange(String s) {
//        listAdapter.filterData(s);
        if (TextUtils.isEmpty(s)) {
//                _listViewItems.clearTextFilter();
            ((ListAdapter) _listViewItems.getAdapter()).getFilter().filter(null);
        } else {
            ((ListAdapter) _listViewItems.getAdapter()).getFilter().filter(s);
        }
        if (null != _onSearchTextChanged) {
            _onSearchTextChanged.onSearchTextChanged(s);
        }
        return true;
    }


    public interface SearchableItem<T> extends Serializable {
        void onSearchableItemClicked(T item, int position);
    }


    public interface OnSearchTextChanged {
        void onSearchTextChanged(String strText);
    }

}
