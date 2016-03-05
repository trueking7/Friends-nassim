package ca.truewebdev.friends;


import android.content.ContentResolver;
import android.os.Bundle;

import java.util.List;

/**
 * Created by True on 3/3/2016.
 */
public class FriendsListFragment extends android.support.v4.app.ListFragment
        implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<Friend>> {

    private static final String LOG_TAG = FriendsListFragment.class.getSimpleName();
    private FriendsCustomAdapter mAdapter;
    private static final int LOADER_ID = 1;
    private ContentResolver mContentResolver;
    private List<Friend> mFriends;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        mContentResolver = getActivity().getContentResolver();
        mAdapter = new FriendsCustomAdapter(getActivity(), getChildFragmentManager());
        setEmptyText("No friends");
        setListAdapter(mAdapter);
        setListShown(false);

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public android.support.v4.content.Loader<List<Friend>> onCreateLoader(int i, Bundle args) {
        mContentResolver = getActivity().getContentResolver();
        return new FriendsListLoader(getActivity(), FriendsContract.URI_TABLE, mContentResolver);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<Friend>> loader, List<Friend> friends) {
        mAdapter.setData(friends);
        mFriends = friends;
        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<Friend>> loader) {
        mAdapter.setData(null);
    }
}
