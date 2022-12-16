package com.example.lx;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lx.data.BookItem;
import com.example.lx.data.DataSaver;
import com.example.lx.view.SlideMenu;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.hjq.toast.ToastUtils;

import java.util.ArrayList;

public class BookListMainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final int MENU_ID_DETAIL = 1;
    public static final int MENU_ID_UPDATE = 2;
    public static final int MENU_ID_DELETE = 3;
    private ArrayList<BookItem> bookItems;
    private MainRecycleViewAdapter mainRecycleViewAdapter;
    //悬浮按钮对象
    private FloatingActionMenu mActionAddButton;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    //菜单栏实现
    private ImageView btn_back;
    private SlideMenu slideMenu;
    //搜索栏实现
    private EditText mEditText;
    private ImageView mImageView;
    private TextView mTextView;
    Context context;

    //about实现
    private TextView About_text;

    //setting实现
    private TextView set_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        //主界面
        setContentView(R.layout.activity_main);
        context = this;


        btn_back = (ImageView)findViewById(R.id.menu_book_edit_save);
        slideMenu = (SlideMenu)findViewById(R.id.slideMenu);
        //点击返回键打开或关闭Menu
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideMenu.switchMenu();
            }
        });


        RecyclerView recyclerViewMain=findViewById(R.id.booklist_recycler_view);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        //load数据
        DataSaver dataSaver=new DataSaver();
        bookItems =dataSaver.Load(this);
        if(bookItems.size() == 0){
            bookItems.add(0,new BookItem("lx",
                    "lx",
                    "lx",
                    "lx",
                    "lx",
                    "xl",
                    "lx",
                    "lx_app",
                    "https://github.com/l-wave/lx_study"));
        }
        mainRecycleViewAdapter= new MainRecycleViewAdapter(bookItems);

        recyclerViewMain.setAdapter(mainRecycleViewAdapter);
        //设置悬浮按钮点击事件的监听
        setFloatingActionButton();
        //设置搜索
        initView();
        //设置about
        setAboutActionButton();
        //设置Setting
        setActionButton();
    }


    //搜索框
    private void initView() {
        mTextView = (TextView) findViewById(R.id.textview);
        mEditText = (EditText) findViewById(R.id.edittext);
        mImageView = (ImageView) findViewById(R.id.imageview);

        //设置删除图片的点击事件
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把EditText内容设置为空
                mEditText.setText("");

            }
        });

        //EditText添加监听
        mEditText.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}//文本改变之前执行

            @Override
            //文本改变的时候执行
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //如果长度为0
                if (s.length() == 0) {
                    //隐藏“删除”图片
                    mImageView.setVisibility(View.GONE);
                } else {//长度不为0
                    //显示“删除图片”
                    mImageView.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void afterTextChanged(Editable s) {//文本改变之后执行

            }

        });

        mTextView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //如果输入框内容为空，提示请输入搜索内容
                String str = mEditText.getText().toString().trim();
                if(str.isEmpty()){
                    ToastUtils.show("请输入您要搜索的内容");
                }else {
                    //判断cursor是否为空
                    int l = bookItems.size();
                    String name;
                    for(int i = 0;i < l;i++) {
                        name = bookItems.get(i).getTitle();
                        if(name.equals(str)) {
                            showListView(i);
                        }

                    }



//                    if (columnCount == 0) {
//                        ToastUtils.show("对不起，没有你要搜索的内容");
//                    }

                }

            }
        });
    }

    private void showListView(int i) {

        Intent intentdetail=new Intent(this, InputBookActivity.class);
        intentdetail.putExtra("position",i);
        intentdetail.putExtra("title", bookItems.get(i).getTitle());
        intentdetail.putExtra("authors", bookItems.get(i).getAuthor());
        intentdetail.putExtra("translators", bookItems.get(i).getTranslator());
        intentdetail.putExtra("publisher", bookItems.get(i).getPublisher());
        intentdetail.putExtra("isbn", bookItems.get(i).getIsbn());
        intentdetail.putExtra("pubTime", bookItems.get(i).getPubTime());
        intentdetail.putExtra("note", bookItems.get(i).getNote());
        intentdetail.putExtra("label", bookItems.get(i).getLabel());
        intentdetail.putExtra("website", bookItems.get(i).getWebsite());
        detailDataLauncher.launch(intentdetail);

    }


//添加对象
    private ActivityResultLauncher<Intent> addDataLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            ,result -> {
                if(result != null){
                    Intent intent=result.getData();
                    if(result.getResultCode()==InputBookActivity.RESULT_CODE_SUCCESS)
                    {
                        Bundle bundle=intent.getExtras();
                        String title= bundle.getString("title");
                        String author= bundle.getString("author");
                        String translator= bundle.getString("translator");
                        String publisher= bundle.getString("publisher");
                        String isbn= bundle.getString("isbn");
                        String pubTime= bundle.getString("pubTime");
                        String note= bundle.getString("note");
                        String label= bundle.getString("label");
                        String website= bundle.getString("website");

                        int position=bundle.getInt("position");
                        bookItems.add(position+1, new BookItem(title,author,translator,publisher,isbn,pubTime,note,label,website) );
                        new DataSaver().Save(this, bookItems);
                        mainRecycleViewAdapter.notifyItemInserted(position+1);
                    }
                }
            });
    //更新对象
    private ActivityResultLauncher<Intent> updateDataLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            ,result -> {
                if(null!=result){
                    Intent intent=result.getData();
                    if(result.getResultCode()==InputBookActivity.RESULT_CODE_SUCCESS)
                    {
                        Bundle bundle=intent.getExtras();
                        String title= bundle.getString("title");
                        String author= bundle.getString("author");
                        String translator= bundle.getString("translator");
                        String publisher= bundle.getString("publisher");
                        String isbn= bundle.getString("isbn");
                        String pubTime= bundle.getString("pubTime");
                        String note= bundle.getString("note");
                        String label= bundle.getString("label");
                        String website= bundle.getString("website");
                        int position=bundle.getInt("position");

                        bookItems.get(position).setTitle(title);
                        bookItems.get(position).setAuthor(author);
                        bookItems.get(position).setTranslator(translator);
                        bookItems.get(position).setPublisher(publisher);
                        bookItems.get(position).setIsbn(isbn);
                        bookItems.get(position).setPubTime(pubTime);
                        bookItems.get(position).setNote(note);
                        bookItems.get(position).setLabel(label);
                        bookItems.get(position).setWebsite(website);

                        new DataSaver().Save(this, bookItems);
                        mainRecycleViewAdapter.notifyItemChanged(position);
                    }
                }
            });
//查看对象具体细节
    private ActivityResultLauncher<Intent> detailDataLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            ,result -> {
                if(null!=result){
                    if(result.getResultCode()==InputBookActivity.RESULT_CODE_SUCCESS)
                    {
                    }
                }
            });
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case MENU_ID_DETAIL:
                Intent intentdetail=new Intent(this, InputBookActivity.class);
                intentdetail.putExtra("position",item.getOrder());
                intentdetail.putExtra("title", bookItems.get(item.getOrder()).getTitle());
                intentdetail.putExtra("authors", bookItems.get(item.getOrder()).getAuthor());
                intentdetail.putExtra("translators", bookItems.get(item.getOrder()).getTranslator());
                intentdetail.putExtra("publisher", bookItems.get(item.getOrder()).getPublisher());
                intentdetail.putExtra("isbn", bookItems.get(item.getOrder()).getIsbn());
                intentdetail.putExtra("pubTime", bookItems.get(item.getOrder()).getPubTime());
                intentdetail.putExtra("note", bookItems.get(item.getOrder()).getNote());
                intentdetail.putExtra("label", bookItems.get(item.getOrder()).getLabel());
                intentdetail.putExtra("website", bookItems.get(item.getOrder()).getWebsite());
                detailDataLauncher.launch(intentdetail);
                break;
            case MENU_ID_UPDATE:
                Intent intentUpdate=new Intent(this, InputBookActivity.class);
                intentUpdate.putExtra("position",item.getOrder());
                intentUpdate.putExtra("title", bookItems.get(item.getOrder()).getTitle());
                intentUpdate.putExtra("authors", bookItems.get(item.getOrder()).getAuthor());
                intentUpdate.putExtra("translators", bookItems.get(item.getOrder()).getTranslator());
                intentUpdate.putExtra("publisher", bookItems.get(item.getOrder()).getPublisher());
                intentUpdate.putExtra("isbn", bookItems.get(item.getOrder()).getIsbn());
                intentUpdate.putExtra("pubTime", bookItems.get(item.getOrder()).getPubTime());
                intentUpdate.putExtra("note", bookItems.get(item.getOrder()).getNote());
                intentUpdate.putExtra("label", bookItems.get(item.getOrder()).getLabel());
                intentUpdate.putExtra("website", bookItems.get(item.getOrder()).getWebsite());
                updateDataLauncher.launch(intentUpdate);
                break;
            case MENU_ID_DELETE:
                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.confirmation)
                        .setMessage(R.string.sure_to_delete)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                bookItems.remove(item.getOrder());
                                new DataSaver().Save(BookListMainActivity.this, bookItems);
                                mainRecycleViewAdapter.notifyItemRemoved(item.getOrder());
                            }
                        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
                alertDialog.show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    //设置ABOUT按钮
    private void setAboutActionButton() {
        About_text = (TextView)findViewById(R.id.About_button);
        //点击启动activity
        About_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookListMainActivity.this,AboutActivity.class));
            }
        });

    }

    //设置Settings按钮
    private void setActionButton() {
        set_text = (TextView)findViewById(R.id.set_button);
        //点击启动activity
        set_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookListMainActivity.this,SettingActivity.class));
            }
        });

    }
    //设置悬浮按钮
    private void setFloatingActionButton() {

        mActionAddButton = (FloatingActionMenu) findViewById(R.id.fab_menu_add);
        fab1 = (FloatingActionButton) findViewById(R.id.fab_menu_item_1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i(TAG, "fab menu item 1 clicked");
                Intent intent= new Intent(BookListMainActivity.this,InputBookActivity.class);
                intent.putExtra("position",-1);
                addDataLauncher.launch(intent);

            }
        });
        fab2 = (FloatingActionButton) findViewById(R.id.fab_menu_item_2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i(TAG, "fab menu item 2 clicked");
                Intent intent= new Intent(BookListMainActivity.this,InputBookActivity.class);
                intent.putExtra("position",-1);
                addDataLauncher.launch(intent);
            }
        });
    }

    //获取书本列表方法
    public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder> {

        private ArrayList<BookItem> localDataSet;

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */

        //根据自己要展示的视图而定
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewTitle;
            private final TextView textViewAuthor;
//            private final TextView textViewTranslator;
            private final TextView textViewPublisher;
//            private final TextView textViewIsbn;
            private final TextView textViewPubTime;


            public ViewHolder(View view) {
                super(view);
                // Define click listener for the ViewHolder's View
                textViewAuthor = view.findViewById(R.id.list_author_text_view);
//                textViewTranslator = view.findViewById(R.id.book_translator_edit_text);
                textViewPublisher = view.findViewById(R.id.list_publisher_text_view);
//                textViewIsbn = view.findViewById(R.id.book_isbn_edit_text);
                textViewPubTime = view.findViewById(R.id.list_pubtime_text_view);

                textViewTitle = view.findViewById(R.id.list_title_text_view);

                view.setOnCreateContextMenuListener(this);
            }

            public TextView getTextViewTitle() {
                return textViewTitle;
            }
            public TextView gettextViewAuthor() {
                return textViewAuthor;
            }
//            public TextView gettextViewTranslator() {
//                return textViewTranslator;
//            }
            public TextView gettextViewPublisher() {
                return textViewPublisher;
            }
//            public TextView gettextViewIsbn() {
//                return textViewIsbn;
//            }
            public TextView gettextViewPubTime() {
                return textViewPubTime;
            }


            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                int position = getAdapterPosition();
                contextMenu.add(0,MENU_ID_DETAIL,getAdapterPosition(),"Detail");
                contextMenu.add(0,MENU_ID_UPDATE,getAdapterPosition(), "Update "+localDataSet.get(position).getTitle());
                contextMenu.add(0,MENU_ID_DELETE,getAdapterPosition(), "Delete "+localDataSet.get(position).getTitle());
            }
        }

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used
         * by RecyclerView.
         */
        public MainRecycleViewAdapter(ArrayList<BookItem> dataSet) {
            localDataSet = dataSet;
        }

        // Create new views (invoked by the layout manager)
        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.booklist_main, viewGroup, false);

            return new ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.getTextViewTitle().setText(localDataSet.get(position).getTitle());
            viewHolder.gettextViewAuthor().setText(localDataSet.get(position).getAuthor());
//            viewHolder.gettextViewTranslator().setText(localDataSet.get(position).getTranslator());
            viewHolder.gettextViewPublisher().setText(localDataSet.get(position).getPublisher());
//            viewHolder.gettextViewIsbn().setText(localDataSet.get(position).getIsbn());
            viewHolder.gettextViewPubTime().setText(localDataSet.get(position).getPubTime());
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }
}