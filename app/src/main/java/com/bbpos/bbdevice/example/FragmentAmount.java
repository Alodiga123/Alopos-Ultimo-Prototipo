package com.bbpos.bbdevice.example;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.bbpos.bbdevice.BBDeviceController;

import java.util.Locale;

import static com.bbpos.bbdevice.example.BaseActivity.bbDeviceController;
/**
 * Created by usuario on 14/06/17.
 */

public class FragmentAmount extends Fragment{
    View rootView = null;
    String transactionTypeString;
    String accountType;
    protected static String cashbackAmount = "";
    private boolean flagsaving = false;
    private boolean flagchecking = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_amount,container,false);
        Button confirm = (Button) rootView.findViewById(R.id.confirmButton);
        final EditText amountEditText = (EditText) rootView.findViewById(R.id.amountEditText);
        final BaseActivity activity = (BaseActivity) getActivity();
        final RadioButton savings = (RadioButton) rootView.findViewById(R.id.ahorro);
        final RadioButton checking = (RadioButton) rootView.findViewById(R.id.corriente);

        savings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(savings.isChecked()){
                    if(!flagsaving){
                        savings.setChecked(true);
                        checking.setChecked(false);
                        flagsaving = true;
                        flagchecking = false;
                    }
                    else{
                        flagsaving=false;
                        savings.setChecked(false);
                        checking.setChecked(false);
                    }
                }
            }
        });
        checking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checking.isChecked()){
                    if(!flagchecking){
                        checking.setChecked(true);
                        savings.setChecked(false);
                        flagchecking = true;
                        flagsaving = false;
                    }
                    else{
                        flagchecking=false;
                        savings.setChecked(false);
                        checking.setChecked(false);
                    }
                }
            }
        });



        //String[] symbols = new String[] { "DOLLAR", "RUPEE", "YEN", "POUND", "EURO", "WON", "DIRHAM", "RIYAL", "AED", "BS.", "YUAN", "NEW_SHEKEL", "NULL" };
        //((Spinner) dialog.findViewById(R.id.symbolSpinner)).setAdapter(new ArrayAdapter<String>(currentActivity, android.R.layout.simple_spinner_item, symbols));

        //String[] transactionTypes = new String[] { "GOODS", "SERVICES", "CASHBACK", "INQUIRY", "TRANSFER", "PAYMENT", "REFUND", "VOID", "REVERSAL" };
        //((Spinner) dialog.findViewById(R.id.transactionTypeSpinner)).setAdapter(new ArrayAdapter<String>(currentActivity, android.R.layout.simple_spinner_item, transactionTypes));

        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String amount = amountEditText.getText().toString();


                // Check which radio button was clicked
                switch(rootView.getId()) {
                    case R.id.ahorro:
                        if (savings.isChecked())
                            accountType=getString(R.string.Saving);
                            break;
                    case R.id.corriente:
                        if (checking.isChecked())
                            accountType=getString(R.string.Checking);
                            break;
                }
                //String cashbackAmount = ((EditText) (dialog.findViewById(R.id.cashbackAmountEditText))).getText().toString();
                //String transactionTypeString = (String) ((Spinner) dialog.findViewById(R.id.transactionTypeSpinner)).getSelectedItem();
                //String symbolString = (String) ((Spinner) dialog.findViewById(R.id.symbolSpinner)).getSelectedItem();
                String symbolString = "BS.";
                transactionTypeString=getString(R.string.Goods);

                BBDeviceController.TransactionType transactionType = BBDeviceController.TransactionType.GOODS;
                if (transactionTypeString.equals(getString(R.string.Goods))) {
                    transactionType = BBDeviceController.TransactionType.GOODS;
                } else if (transactionTypeString.equals("SERVICES")) {
                    transactionType = BBDeviceController.TransactionType.SERVICES;
                } else if (transactionTypeString.equals("CASHBACK")) {
                    transactionType = BBDeviceController.TransactionType.CASHBACK;
                } else if (transactionTypeString.equals("INQUIRY")) {
                    transactionType = BBDeviceController.TransactionType.INQUIRY;
                } else if (transactionTypeString.equals("TRANSFER")) {
                    transactionType = BBDeviceController.TransactionType.TRANSFER;
                } else if (transactionTypeString.equals("PAYMENT")) {
                    transactionType = BBDeviceController.TransactionType.PAYMENT;
                } else if (transactionTypeString.equals("REFUND")) {
                    transactionType = BBDeviceController.TransactionType.REFUND;
                } else if (transactionTypeString.equals("VOID")) {
                    transactionType = BBDeviceController.TransactionType.VOID;
                } else if (transactionTypeString.equals("REVERSAL")) {
                    transactionType = BBDeviceController.TransactionType.REVERSAL;
                }

                BBDeviceController.CurrencyCharacter[] currencyCharacters = new BBDeviceController.CurrencyCharacter[] { BBDeviceController.CurrencyCharacter.A, BBDeviceController.CurrencyCharacter.B, BBDeviceController.CurrencyCharacter.C };
                if (symbolString.equals("DOLLAR")) {
                    currencyCharacters = new BBDeviceController.CurrencyCharacter[] { BBDeviceController.CurrencyCharacter.DOLLAR };
                } else if (symbolString.equals("RUPEE")) {
                    currencyCharacters = new BBDeviceController.CurrencyCharacter[] { BBDeviceController.CurrencyCharacter.RUPEE };
                } else if (symbolString.equals("YEN")) {
                    currencyCharacters = new BBDeviceController.CurrencyCharacter[] { BBDeviceController.CurrencyCharacter.YEN };
                } else if (symbolString.equals("POUND")) {
                    currencyCharacters = new BBDeviceController.CurrencyCharacter[] { BBDeviceController.CurrencyCharacter.POUND };
                } else if (symbolString.equals("EURO")) {
                    currencyCharacters = new BBDeviceController.CurrencyCharacter[] { BBDeviceController.CurrencyCharacter.EURO };
                } else if (symbolString.equals("WON")) {
                    currencyCharacters = new BBDeviceController.CurrencyCharacter[] { BBDeviceController.CurrencyCharacter.WON };
                } else if (symbolString.equals("DIRHAM")) {
                    currencyCharacters = new BBDeviceController.CurrencyCharacter[] { BBDeviceController.CurrencyCharacter.DIRHAM };
                } else if (symbolString.equals("RIYAL")) {
                    currencyCharacters = new BBDeviceController.CurrencyCharacter[] { BBDeviceController.CurrencyCharacter.RIYAL, BBDeviceController.CurrencyCharacter.RIYAL_2 };
                } else if (symbolString.equals("AED")) {
                    currencyCharacters = new BBDeviceController.CurrencyCharacter[] { BBDeviceController.CurrencyCharacter.A, BBDeviceController.CurrencyCharacter.E, BBDeviceController.CurrencyCharacter.D };
                } else if (symbolString.equals("BS.")) {
                    currencyCharacters = new BBDeviceController.CurrencyCharacter[] { BBDeviceController.CurrencyCharacter.B, BBDeviceController.CurrencyCharacter.S, BBDeviceController.CurrencyCharacter.DOT };
                } else if (symbolString.equals("YUAN")) {
                    currencyCharacters = new BBDeviceController.CurrencyCharacter[] { BBDeviceController.CurrencyCharacter.YUAN };
                } else if (symbolString.equals("NEW_SHEKEL")) {
                    currencyCharacters = new BBDeviceController.CurrencyCharacter[] { BBDeviceController.CurrencyCharacter.NEW_SHEKEL };
                } else if (symbolString.equals("NULL")) {
                    currencyCharacters = null;
                }

                String currencyCode;
                if (Locale.getDefault().getCountry().equalsIgnoreCase("CN")) {
                    currencyCode = "156";
                    currencyCharacters = new BBDeviceController.CurrencyCharacter[] { BBDeviceController.CurrencyCharacter.YUAN };
                } else {
                    currencyCode = "840";
                }

                if (bbDeviceController.setAmount(amount, cashbackAmount, currencyCode, transactionType, currencyCharacters)) {
    //                amountEditText.setText("BS." + amount);
                    activity.amount = amount;
                    activity.cashbackAmount = cashbackAmount;
                    activity.statusEditText.setText(getString(R.string.please_confirm_amount));

                } else {
                    activity.promptForAmount();
                }
            }

        });

        rootView.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                bbDeviceController.cancelSetAmount();
                activity.stopConnection();
            }

        });
    return rootView;
    }
}
