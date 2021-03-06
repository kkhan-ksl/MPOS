package com.bbaf.mpos.saleledger.ui;

import java.util.ArrayList;
import java.util.zip.Inflater;

import com.bbaf.mpos.R;
import com.bbaf.mpos.FacadeController.Register;
import com.bbaf.mpos.ProductDescription.ProductDescription;
import com.bbaf.mpos.sale.Sale;
import com.bbaf.mpos.sale.SaleLineItem;
import com.bbaf.mpos.saleledger.ProductLedger;
import com.bbaf.mpos.saleledger.SaleLedger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A class adapt ArrayList<ProductLedger> to be ListView shown in Sale view.
 * @author Poramet Homprakob 5510546077
 */
public class ProductLedgerListViewAdapter extends BaseAdapter {
	
	private Activity activity;
	private ArrayList<ProductLedger> productList;

	/**
	 * Constructor, using calling activity.
	 * @param activity calling activity
	 * @param saleList list of ProductLedger
	 */
	public ProductLedgerListViewAdapter(Activity activity, ArrayList<ProductLedger> productList) {
		this.activity = activity;
		this.productList = productList;
	}

	@Override
	public int getCount() {
		return productList.size();
	}

	@Override
	public Object getItem(int position) {
		return productList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return new ProductLedgerListRow(activity, productList.get(position));
	}	
}
