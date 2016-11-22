package com.example.nn.qlsinhvien.Activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.nn.qlsinhvien.R;
import com.example.nn.qlsinhvien.db.SinhvienHelper;
import com.example.nn.qlsinhvien.models.Sinhvien;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvSinhvien;
    private Button btAdd;
    SinhvienHelper sv;
    private final List<Sinhvien> sinhvienList = new ArrayList<Sinhvien>();
    private ArrayAdapter<Sinhvien> listViewAdapter;

    private static final int MENU_ITEM_UPDATE = 222;
    private static final int MENU_ITEM_DELETE = 333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvSinhvien = (ListView) findViewById(R.id.lv_sinhvien);
        sv = new SinhvienHelper(MainActivity.this);
        showContact(lvSinhvien);
        btAdd = (Button) findViewById(R.id.bt_add);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(MainActivity.this);
                View customDialogView = li.inflate(R.layout.sinhvien_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setView(customDialogView);

                final EditText etMssv = (EditText) customDialogView.findViewById(R.id.et_mssv);
                final EditText etHoten = (EditText) customDialogView.findViewById(R.id.et_hoten);
                final EditText etLop = (EditText) customDialogView.findViewById(R.id.et_lop);

                alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                addSinhvien(etMssv.getText().toString(),etHoten.getText().toString(),etLop.getText().toString());
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }
    private void showContact(ListView lvsinhvien) {
        List<Sinhvien> list = sv.getAllsinhvien();
        sinhvienList.addAll(list);
        this.listViewAdapter = new ArrayAdapter<Sinhvien>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, this.sinhvienList);


        // Đăng ký Adapter cho ListView.
        lvsinhvien.setAdapter(this.listViewAdapter);

        // Đăng ký Context menu cho ListView.
        registerForContextMenu(lvsinhvien);
    }

    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo)    {

        super.onCreateContextMenu(menu, view, menuInfo);
        menu.setHeaderTitle("Select The Action");

        // groupId, itemId, order, title
        menu.add(0, MENU_ITEM_UPDATE , 1, "Update Student");
        menu.add(0, MENU_ITEM_DELETE, 2, "Delete Student");
    }
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Sinhvien selected = (Sinhvien) this.lvSinhvien.getItemAtPosition(info.position);

        if(item.getItemId() == MENU_ITEM_UPDATE){
            LayoutInflater li = LayoutInflater.from(MainActivity.this);
            View customDialogView = li.inflate(R.layout.sinhvien_dialog, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setView(customDialogView);

            final EditText etMssv = (EditText) customDialogView.findViewById(R.id.et_mssv);
            final EditText etHoten = (EditText) customDialogView.findViewById(R.id.et_hoten);
            final EditText etLop = (EditText) customDialogView.findViewById(R.id.et_lop);

            etMssv.setText(selected.getMSSV());
            etHoten.setText(selected.getHOTEN());
            etLop.setText(selected.getLOP());

            alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            updateSinhvien(selected.getID(),etMssv.getText().toString(),etHoten.getText().toString(),etLop.getText().toString());
                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else if(item.getItemId() == MENU_ITEM_DELETE){
            // Hỏi trước khi xóa.
            new AlertDialog.Builder(this)
                    .setTitle("Confirm")
                    .setMessage(selected.getMSSV()+". Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            deleteSinhvien(selected);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }
        else {
            return false;
        }
        return true;
    }

    private void updateSinhvien(int id, String mssv, String hoten,String lop) {
        sv = new SinhvienHelper(this);
        Sinhvien sinhvien = new Sinhvien();
        sinhvien.setID(id);
        sinhvien.setMSSV(mssv);
        sinhvien.setHOTEN(hoten);
        sinhvien.setLOP(lop);
        sv.updatesinhvien(sinhvien);
        sinhvienList.clear();
        List<Sinhvien> list = sv.getAllsinhvien();
        sinhvienList.addAll(list);
        listViewAdapter.notifyDataSetChanged();
    }

    private void deleteSinhvien(Sinhvien sinhvien) {
        sv = new SinhvienHelper(MainActivity.this);
        sv.deletesinhvien(sinhvien);
        this.sinhvienList.remove(sinhvien);
        this.listViewAdapter.notifyDataSetChanged();
    }

    private  void addSinhvien(String mssv,String hoten,String lop){
        sv = new SinhvienHelper(MainActivity.this);
        Sinhvien sinhvien = new Sinhvien();
        sinhvien.setMSSV(mssv);
        sinhvien.setHOTEN(hoten);
        sinhvien.setLOP(lop);
        sv.insertsinhvien(sinhvien);
        sinhvienList.clear();
        List<Sinhvien> list = sv.getAllsinhvien();
        sinhvienList.addAll(list);
        listViewAdapter.notifyDataSetChanged();
    }
}
