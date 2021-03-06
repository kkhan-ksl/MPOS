package com.bbaf.mpos.inventoryAndSale.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bbaf.mpos.R;
import com.bbaf.mpos.FacadeController.Register;
import com.bbaf.mpos.ProductDescription.ProductDescription;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Controller of EditProductActivity.
 * @author Poramet Homprakob 5510546077
 */
public class EditProductActivity extends Activity {

	private EditText editTextProductId;
	private EditText editTextProductName;
	private EditText editTextPrice;
	private EditText editTextCost;
	private EditText editTextQuantity;
	private Button buttonScan;
	private Button buttonSave;
	private Button buttonCancel;

	private static final int SCANNER_ACTIVITY_REQUESTCODE = 49374;
	private static final int EDIT_CANCEL = 0;
	private static final int EDIT_SUCCESS = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_product);
		final ProductDescription oldProduct = (ProductDescription) getIntent()
				.getSerializableExtra("ProductDescription");
		final int oldQuantity = getIntent()
				.getIntExtra("ProductQuantity", 0);

		editTextProductId = (EditText) findViewById(R.id.edit_editTextProductId);
		editTextProductId.setText(String.valueOf(oldProduct.getId()));
		editTextProductName = (EditText) findViewById(R.id.edit_editTextProductName);
		editTextProductName.setText(String.valueOf(oldProduct.getName()));
		editTextPrice = (EditText) findViewById(R.id.edit_editTextPrice);
		editTextPrice.setText(String.format("%.2f", oldProduct.getPrice()));
		editTextCost = (EditText) findViewById(R.id.edit_editTextCost);
		editTextCost.setText(String.format("%.2f", oldProduct.getCost()));
		editTextQuantity = (EditText) findViewById(R.id.edit_editTextQuantity);
		editTextQuantity.setText(String.valueOf(oldQuantity));
		buttonScan = (Button) findViewById(R.id.buttonScan);
		buttonScan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				IntentIntegrator scanIntegrator = new IntentIntegrator(
						EditProductActivity.this);
				scanIntegrator.initiateScan();
			}
		});
		
		buttonSave = (Button) findViewById(R.id.buttonSave);
		buttonSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String id = editTextProductId.getText().toString();
				if (id.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Product ID must not be empty.", Toast.LENGTH_SHORT)
							.show();
				}
				else {
					if (Register.getInstance().getInventory().getProduct(id) != null && !id.equals(oldProduct.getId())) {
						Toast.makeText(
								getApplicationContext(),
								String.format("Product : %s is already added.",
										id), Toast.LENGTH_SHORT).show();
					}
					else {
						String name = editTextProductName.getText().toString();
						double price = Double.parseDouble(editTextPrice
								.getText().toString());
						double cost = Double.parseDouble(editTextCost.getText()
								.toString());
						ProductDescription newProduct = new ProductDescription(
								id, name, price, cost);
						Register.getInstance().getInventory().editProduct(oldProduct, newProduct);
						int quantity = Integer.parseInt(editTextQuantity
								.getText().toString());
						Register.getInstance().getInventory().editQuantity(oldProduct, newProduct, quantity);
						setResult(EDIT_SUCCESS);
						finish();
					}
				}
			}
		});

		buttonCancel = (Button) findViewById(R.id.buttonCancel);
		buttonCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setResult(EDIT_SUCCESS);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit_product, menu);
		return true;
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == SCANNER_ACTIVITY_REQUESTCODE) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

            // bat: although scanner is canceled, scanningResult is not null but scanningResult.getContents()
        	if (scanningResult != null) {
        		editTextProductId.setText(scanningResult.getContents());
        		editTextProductName.requestFocus();
        	}
        	else {
        	    Toast.makeText(getApplicationContext(),"No scanned data received!", Toast.LENGTH_SHORT).show();
        	}
		}
	}
}
