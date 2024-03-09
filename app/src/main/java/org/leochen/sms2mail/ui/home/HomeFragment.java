package org.leochen.sms2mail.ui.home;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.leochen.sms2mail.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private SimpleAdapter sa;
    private List<Map<String, Object>> data;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ListView listView = binding.listView;
        data = new ArrayList<Map<String, Object>>();
        //配置适配置器Sa
        sa = new SimpleAdapter( this.getContext(), data, android.R.layout.simple_list_item_2,
                new String[]{"address", "smsBody"}, new int[]{android.R.id.text1,
                android.R.id.text2} );
        listView.setAdapter( sa );

        checkSMSPermission();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkSMSPermission();
    }

    public static final int REQ_CODE_CONTACT = 1;
    private void checkSMSPermission() {
        if (ContextCompat.checkSelfPermission( this.getContext(), Manifest.permission.READ_SMS )
                != PackageManager.PERMISSION_GRANTED) {
            //未获取到读取短信权限,向系统申请权限
            ActivityCompat.requestPermissions( this.getActivity(),
                    new String[]{Manifest.permission.READ_SMS}, REQ_CODE_CONTACT );
        } else {
            query();
        }
    }

    private void query() {
        //读取所有短信
        Uri uri = Uri.parse( "content://sms/inbox" );   //通过content://sms/这个URI访问SMS数据库
        ContentResolver resolver = this.getContext().getContentResolver();
        Cursor cursor = resolver.query( uri, new String[]{"_id", "address", "body", "date", "type"}, null, null, null );
        if (cursor != null && cursor.getCount() > 0) {
            int _id;
            String address;
            String smsBody;
            Long date;
            int type;
            while (cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<String, Object>();

                _id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                type = cursor.getInt(cursor.getColumnIndexOrThrow("type"));
                smsBody = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                date = cursor.getLong(cursor.getColumnIndexOrThrow("date"));
                Date dateSent = new Date(date);

                map.put( "address", address );
                map.put( "smsBody", smsBody );



                Log.i( "test", "_id=" + _id + " address=" + address + " body=" + smsBody + " date=" + date + " type=" + type );
                data.add( map );
                //通知适配器发生改变
                sa.notifyDataSetChanged();
            }
        }
    }


}