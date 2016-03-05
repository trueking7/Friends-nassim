package ca.truewebdev.friends;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by True on 3/4/2016.
 */
public class FriendsDialog extends DialogFragment {

    private LayoutInflater mLayoutInflater;
    public static final String LOG_TAG = FriendsDialog.class.getSimpleName();
    public static final String DIALOG_TYPE = "command";
    public static final String DELETE_RECORD = "deleteRecord";
    public static final String DELETE_DATABASE = "deleteDatabase";
    public static final String CONFIRM_EXIT = "confirmExit";


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mLayoutInflater = getActivity().getLayoutInflater();
        final View view = mLayoutInflater.inflate(R.layout.friend_layout, null);
        String command = getArguments().getString(DIALOG_TYPE);
        if (command.equals(DELETE_RECORD)) {

            final int _id = getArguments().getInt(FriendsContract.FriendsColumns.FRIENDS_ID);
            String name = getArguments().getString(FriendsContract.FriendsColumns.FRIENDS_NAME);
            TextView popupMessage = (TextView) view.findViewById(R.id.popup_message);
            popupMessage.setText("Are you sure you want to delete " + name + " from your friends list?");
            builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ContentResolver contentResolver = getActivity().getContentResolver();
                    Uri uri = FriendsContract.Friends.buildFriendUri(String.valueOf(_id));
                    contentResolver.delete(uri, null, null);
                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        } else if (command.equals(DELETE_DATABASE)) {
            TextView popupMessage = (TextView) view.findViewById(R.id.popup_message);
            popupMessage.setText("Are you sure you want to delete the entire database?");
            builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ContentResolver contentResolver = getActivity().getContentResolver();
                    Uri uri = FriendsContract.URI_TABLE;
                    contentResolver.delete(uri, null, null);
                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        } else if (command.equals(CONFIRM_EXIT)) {
            TextView popupMessage = (TextView) view.findViewById(R.id.popup_message);
            popupMessage.setText("Are you sure you want to exit without saving?");
            builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finish();
                }
            });
        } else {
            Log.d(LOG_TAG, "Invalid command passed as a parameter");
        }

        return builder.create();
    }
}
