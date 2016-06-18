//package com.makemyandroidapp.example.stacksites;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import org.json.JSONObject;
//
//public class InputActivity extends Activity {
//
//    private static EbayInvoke ebayInvoke;
//    private static EbayParser ebayParser;
//    private SearchResult listings;
//    private ListingArrayAdapter adapter;
//    private final static String TAG="ebayDemoActivity";
//    private static ProgressDialog progressDialog;
//    private String searchTerm="phantasy+star+3"; //initial value for demo
//    //menu constants
//    private final static int MENU_KEYWORD=0;
//    private final static int MENU_QUIT=1;
//
//    //filter dialog
//    private AlertDialog keywordDialog;
//    private EditText keywordTextbox;
//
//
//    private TextView mTextView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        ListView listView=this.getListView();
////        listView.setTextFilterEnabled(true);
////        //listView.setOnItemClickListener(selectItemListener);
//        setContentView(R.layout.activity_input);
//
//        // TextViewのインスタンスを取得
//        mTextView = (TextView) findViewById(R.id.editText);
//
//        // リスナーをセット
//        findViewById(R.id.button).setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//
//                Intent data = new Intent(v.getContext(), MainActivity.class);
//                data.putExtra("text", mTextView.getText().toString());
//                startActivity(data);
//
//            }
//        });
//
//
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        try{
//            menu.add(0,MENU_KEYWORD,0,"Search Term");
//            menu.add(0,MENU_QUIT,1,"Quit");
//            return(true);
//        }catch(Exception x){
//            Log.e(TAG,"onCreateOptionsMenu",x);
//            return(false);
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        try{
//            switch(item.getItemId()){
//                case MENU_KEYWORD:{this.showKeywordDialog();return(true);}
//                case MENU_QUIT:{this.finish();return(true);}
//                default:{return(false);}
//            }
//        }catch(Exception x){
//            Log.e(TAG,"onOptionsItemSelected",x);
//            return(false);
//        }
//    }
//
//    //dialog handlers
//
//    DialogInterface.OnClickListener onKeywordDialogCancelListener=new DialogInterface.OnClickListener(){
//        @Override
//        public void onClick(DialogInterface dialog,int which){/*not implemented*/}
//    };
//
//    private void showKeywordDialog(){
//        try{
//            //create the dialog
//            if(this.keywordDialog==null){
//                LayoutInflater inflater=(LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
//                View layout=inflater.inflate(R.layout.searchdialog,(ViewGroup)findViewById(R.id.searchdialog_root));
//                AlertDialog.Builder builder=new AlertDialog.Builder(this);
//                builder.setView(layout);
//                builder.setTitle("Search Keyword");
//                builder.setPositiveButton("OK",onKeywordDialogPositiveListener);
//                builder.setNegativeButton("Cancel",onKeywordDialogCancelListener);
//                this.keywordTextbox=(EditText)layout.findViewById(R.id.searchdialog_keyword);
//                this.keywordDialog=builder.create();
//            }
//            //show the dialog
//            this.keywordDialog.show();
//        }catch(Exception x){
//            Log.e(TAG,"showFilterDialog",x);
//        }
//    }
//
//
//    DialogInterface.OnClickListener onListingDetailDialogCloseListener=new DialogInterface.OnClickListener(){
//        @Override
//        public void onClick(DialogInterface dialog,int which){/*not implemented*/}
//    };
//
//
//    DialogInterface.OnClickListener onKeywordDialogPositiveListener=new DialogInterface.OnClickListener(){
//        @Override
//        public void onClick(DialogInterface dialog,int which){
//            String newSearchTerm=keywordTextbox.getText().toString().replace(" ","+");
//            if(!newSearchTerm.equals(searchTerm)){
//                searchTerm=newSearchTerm;
//                Toast.makeText(getApplicationContext(), searchTerm, Toast.LENGTH_LONG).show();
//                refreshListings();
//            }
//        }
//    };
//
//    private void showErrorDialog(Exception x){
//        try{
//            new AlertDialog.Builder(this)
//                    .setTitle(R.string.app_name)
//                    .setMessage(x.getMessage())
//                    .setPositiveButton("Close", null)
//                    .show();
//        }catch(Exception reallyBadTimes){
//            Log.e(TAG,"showErrorDialog",reallyBadTimes);
//        }
//    }
//
//
//
////execute the search and display results
//
//    private final Handler loadListHandler=new Handler(){
//        public void handleMessage(Message message){
//            loadListAdapter();
//        }
//    };
//
//    private void loadListAdapter(){
//        this.adapter=new ListingArrayAdapter(this,R.layout.listviewitem,listings);
//        //this.setListAdapter(this.adapter);
//        if(progressDialog!=null){
//            progressDialog.cancel();
//        }
//    }
//
//    private class LoadListThread extends Thread{
//        private Handler handler;
//        private Context context;
//
//        public LoadListThread(Handler handler,Context context){
//            this.handler=handler;
//            this.context=context;
//        }
//
//        public void run(){
//            String searchResponse="";
//            try{
//                if(ebayInvoke==null){
//                    ebayInvoke=new EbayInvoke(this.context);
//                }
//                if(ebayParser==null){
//                    ebayParser=new EbayParser(this.context);
//                }
//                searchResponse=ebayInvoke.search(searchTerm);
//                if(listings==null){
//                    listings=new SearchResult();
//                }
//                listings.setListings(ebayParser.parseListings(searchResponse));
//                // JSONObject に変換します
//                JSONObject json = new JSONObject(searchResponse);
//
//
//                Log.d("test", json.toString());
//
//                this.handler.sendEmptyMessage(RESULT_OK);
//
//            }catch(Exception x){
//                Log.e(TAG,"LoadListThread.run(): searchResponse="+searchResponse,x);
//                listings.setError(x);
//                if((progressDialog!=null)&&(progressDialog.isShowing())){
//                    progressDialog.dismiss();
//                }
//                showErrorDialog(x);
//            }
//        }
//    }
//
//    private void refreshListings(){
//        try{
//            if(progressDialog==null){
//                progressDialog=new ProgressDialog(this);
//            }
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setMessage("Searching for auctions...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//            LoadListThread loadListThread=new LoadListThread(this.loadListHandler,this.getApplicationContext());
//            loadListThread.start();
//        }catch(Exception x){
//            Log.e(TAG,"onResume",x);
//            if((progressDialog!=null)&&(progressDialog.isShowing())){
//                progressDialog.dismiss();
//            }
//            this.showErrorDialog(x);
//        }
//    }
//}
//
