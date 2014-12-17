package de.hszg.risikousapp.dialogHelper;

/**
 * Created by Jens on 17.12.2014.
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Environment;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FileDialog
{
    private int fileOpen = 0;
    private int fileSave = 1;
    private int FolderChoose = 2;
    private int selectType = fileSave;
    private String mSdcardDirectory = "";
    private Context m_context;
    private TextView mTitleView1;
    private TextView mTitleView0;
    public String defaultFileName = "default.txt";
    private String selectedFileName = defaultFileName;
    private EditText inputText;

    private String mDir = "";
    private List<String> mSubDirs = null;
    private FileDialogListener mFileDialogListener = null;
    private ArrayAdapter<String> mListAdapter = null;

    //////////////////////////////////////////////////////
    // Callback interface for selected directory
    //////////////////////////////////////////////////////
    public interface FileDialogListener
    {
        public void onChosenDir(String chosenDir);
    }

    public FileDialog(Context context, String file_select_type, FileDialogListener fileDialogListener)
    {
        if (file_select_type.equals("fileOpen"))          selectType = fileOpen;
        else if (file_select_type.equals("fileSave"))     selectType = fileSave;
        else if (file_select_type.equals("FolderChoose")) selectType = FolderChoose;
        else selectType = fileOpen;

        m_context = context;
        mSdcardDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileDialogListener = fileDialogListener;

        try
        {
            mSdcardDirectory = new File(mSdcardDirectory).getCanonicalPath();
        }
        catch (IOException ioe)
        {
        }
    }

    ///////////////////////////////////////////////////////////////////////
    // chooseFile_or_Dir() - load directory chooser dialog for initial
    // default sdcard directory
    ///////////////////////////////////////////////////////////////////////
    public void chooseFile_or_Dir()
    {
        // Initial directory is sdcard directory
        if (mDir.equals(""))	chooseFile_or_Dir(mSdcardDirectory);
        else chooseFile_or_Dir(mDir);
    }

    ////////////////////////////////////////////////////////////////////////////////
    // chooseFile_or_Dir(String dir) - load directory chooser dialog for initial
    // input 'dir' directory
    ////////////////////////////////////////////////////////////////////////////////
    public void chooseFile_or_Dir(String dir)
    {
        File dirFile = new File(dir);
        if (! dirFile.exists() || ! dirFile.isDirectory())
        {
            dir = mSdcardDirectory;
        }

        try
        {
            dir = new File(dir).getCanonicalPath();
        }
        catch (IOException ioe)
        {
            return;
        }

        mDir = dir;
        mSubDirs = getDirectories(dir);

        class SimpleFileDialogOnClickListener implements DialogInterface.OnClickListener
        {
            public void onClick(DialogInterface dialog, int item)
            {
                String m_dir_old = mDir;
                String sel = "" + ((AlertDialog) dialog).getListView().getAdapter().getItem(item);
                if (sel.charAt(sel.length()-1) == '/')	sel = sel.substring(0, sel.length()-1);

                // Navigate into the sub-directory
                if (sel.equals(".."))
                {
                    mDir = mDir.substring(0, mDir.lastIndexOf("/"));
                }
                else
                {
                    mDir += "/" + sel;
                }
                selectedFileName = defaultFileName;

                if ((new File(mDir).isFile())) // If the selection is a regular file
                {
                    mDir = m_dir_old;
                    selectedFileName = sel;
                }

                updateDirectory();
            }
        }

        AlertDialog.Builder dialogBuilder = createDirectoryChooserDialog(dir, mSubDirs,
                new SimpleFileDialogOnClickListener());

        dialogBuilder.setPositiveButton("OK", new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                // Current directory chosen
                // Call registered listener supplied with the chosen directory
                if (mFileDialogListener != null){
                    {
                        if (selectType == fileOpen || selectType == fileSave)
                        {
                            selectedFileName = inputText.getText() +"";
                            mFileDialogListener.onChosenDir(mDir + "/" + selectedFileName);}
                        else
                        {
                            mFileDialogListener.onChosenDir(mDir);
                        }
                    }
                }
            }
        }).setNegativeButton("Cancel", null);

        final AlertDialog dirsDialog = dialogBuilder.create();

        // Show directory chooser dialog
        dirsDialog.show();
    }

    private boolean createSubDir(String newDir)
    {
        File newDirFile = new File(newDir);
        if   (! newDirFile.exists() ) return newDirFile.mkdir();
        else return false;
    }

    private List<String> getDirectories(String dir)
    {
        List<String> dirs = new ArrayList<String>();
        try
        {
            File dirFile = new File(dir);

            // if directory is not the base sd card directory add ".." for going up one directory
            if (! mDir.equals(mSdcardDirectory) ) dirs.add("..");

            if (! dirFile.exists() || ! dirFile.isDirectory())
            {
                return dirs;
            }

            for (File file : dirFile.listFiles())
            {
                if ( file.isDirectory())
                {
                    // Add "/" to directory names to identify them in the list
                    dirs.add( file.getName() + "/" );
                }
                else if (selectType == fileSave || selectType == fileOpen)
                {
                    // Add file names to the list if we are doing a file save or file open operation
                    dirs.add( file.getName() );
                }
            }
        }
        catch (Exception e)	{}

        Collections.sort(dirs, new Comparator<String>()
        {
            public int compare(String o1, String o2)
            {
                return o1.compareTo(o2);
            }
        });
        return dirs;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////                                   START DIALOG DEFINITION                                    //////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    private AlertDialog.Builder createDirectoryChooserDialog(String title, List<String> listItems,
                                                             OnClickListener onClickListener)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(m_context);
        ////////////////////////////////////////////////
        // Create title text showing file select type //
        ////////////////////////////////////////////////
        mTitleView1 = new TextView(m_context);
        mTitleView1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        //mTitleView1.setTextAppearance(m_context, android.R.style.TextAppearance_Large);
        //mTitleView1.setTextColor( m_context.getResources().getColor(android.R.color.black) );

        if (selectType == fileOpen) mTitleView1.setText("Open:");
        if (selectType == fileSave) mTitleView1.setText("Save As:");
        if (selectType == FolderChoose) mTitleView1.setText("Folder Select:");

        //need to make this a variable Save as, Open, Select Directory
        mTitleView1.setGravity(Gravity.CENTER_VERTICAL);
        mTitleView1.setBackgroundColor(-12303292); // dark gray 	-12303292
        mTitleView1.setTextColor(m_context.getResources().getColor(android.R.color.white));

        // Create custom view for AlertDialog title
        LinearLayout titleLayout1 = new LinearLayout(m_context);
        titleLayout1.setOrientation(LinearLayout.VERTICAL);
        titleLayout1.addView(mTitleView1);


        if (selectType == FolderChoose || selectType == fileSave)
        {
            ///////////////////////////////
            // Create New Folder Button  //
            ///////////////////////////////
            Button newDirButton = new Button(m_context);
            newDirButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            newDirButton.setText("New Folder");
            newDirButton.setOnClickListener(new View.OnClickListener()
                                            {
                                                @Override
                                                public void onClick(View v)
                                                {
                                                    final EditText input = new EditText(m_context);

                                                    // Show new folder name input dialog
                                                    new AlertDialog.Builder(m_context).
                                                            setTitle("New Folder Name").
                                                            setView(input).setPositiveButton("OK", new OnClickListener()
                                                    {
                                                        public void onClick(DialogInterface dialog, int whichButton)
                                                        {
                                                            Editable newDir = input.getText();
                                                            String newDirName = newDir.toString();
                                                            // Create new directory
                                                            if ( createSubDir(mDir + "/" + newDirName) )
                                                            {
                                                                // Navigate into the new directory
                                                                mDir += "/" + newDirName;
                                                                updateDirectory();
                                                            }
                                                            else
                                                            {
                                                                Toast.makeText(	m_context, "Failed to create '"
                                                                        + newDirName + "' folder", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }).setNegativeButton("Cancel", null).show();
                                                }
                                            }
            );
            titleLayout1.addView(newDirButton);
        }

        /////////////////////////////////////////////////////
        // Create View with folder path and entry text box //
        /////////////////////////////////////////////////////
        LinearLayout titleLayout = new LinearLayout(m_context);
        titleLayout.setOrientation(LinearLayout.VERTICAL);

        mTitleView0 = new TextView(m_context);
        mTitleView0.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        mTitleView0.setBackgroundColor(-12303292); // dark gray -12303292
        mTitleView0.setTextColor(m_context.getResources().getColor(android.R.color.white));
        mTitleView0.setGravity(Gravity.CENTER_VERTICAL);
        mTitleView0.setText(title);

        titleLayout.addView(mTitleView0);

        if (selectType == fileOpen || selectType == fileSave)
        {
            inputText = new EditText(m_context);
            inputText.setText(defaultFileName);
            titleLayout.addView(inputText);
        }
        //////////////////////////////////////////
        // Set Views and Finish Dialog builder  //
        //////////////////////////////////////////
        dialogBuilder.setView(titleLayout);
        dialogBuilder.setCustomTitle(titleLayout1);
        mListAdapter = createListAdapter(listItems);
        dialogBuilder.setSingleChoiceItems(mListAdapter, -1, onClickListener);
        dialogBuilder.setCancelable(false);
        return dialogBuilder;
    }

    private void updateDirectory()
    {
        mSubDirs.clear();
        mSubDirs.addAll(getDirectories(mDir));
        mTitleView0.setText(mDir);
        mListAdapter.notifyDataSetChanged();
        //#scorch
        if (selectType == fileSave || selectType == fileOpen)
        {
            inputText.setText(selectedFileName);
        }
    }

    private ArrayAdapter<String> createListAdapter(List<String> items)
    {
        return new ArrayAdapter<String>(m_context, android.R.layout.select_dialog_item, android.R.id.text1, items)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View v = super.getView(position, convertView, parent);
                if (v instanceof TextView)
                {
                    // Enable list item (directory) text wrapping
                    TextView tv = (TextView) v;
                    tv.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
                    tv.setEllipsize(null);
                }
                return v;
            }
        };
    }
}
