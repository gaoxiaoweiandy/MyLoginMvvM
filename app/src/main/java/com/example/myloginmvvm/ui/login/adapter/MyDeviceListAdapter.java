package com.example.myloginmvvm.ui.login.adapter;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.myloginmvvm.R;
import com.example.myloginmvvm.bean.Device;

import java.util.ArrayList;

	public class MyDeviceListAdapter<T> extends BaseAdapter {
		private Context mContext;
	    private  ArrayList<Device> mDeviceList;

		public MyDeviceListAdapter(ArrayList<Device> deviceList, Context context) {
			this.mContext = context;
			this.mDeviceList = deviceList;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			Device item = mDeviceList.get(position);
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.mydevice_list_item, null);
				holder = new ViewHolder();
				holder.tvOwnerName = (TextView) convertView.findViewById(R.id.tvName);
				holder.tvDeviceDescribe = (TextView) convertView.findViewById(R.id.tvDescribe);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			String name = item.getDeviceName();
			StringBuffer deviceIMEI = new StringBuffer("设备号");
			deviceIMEI.append(item.getDeviceIMEI());

			//名称
			if(!TextUtils.isEmpty(name)) {
				holder.tvOwnerName.setText(name);
			}
			//描述信息
			if(!TextUtils.isEmpty(deviceIMEI)) {
				holder.tvDeviceDescribe.setVisibility(View.VISIBLE);
				holder.tvDeviceDescribe.setText(deviceIMEI);
			}
			return convertView;
		}
		@Override
		public int getCount() {
			return mDeviceList.size();
		}
		@Override
		public Object getItem(int position) {
			return mDeviceList.get(position);
		}
		@Override
		public long getItemId(int position) {
			return 0;
		}
		class ViewHolder {
			TextView tvOwnerName;
			TextView tvDeviceDescribe;
		}

	}