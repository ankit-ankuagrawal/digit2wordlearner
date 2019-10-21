package com.godavari.digittowordlearner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.godavari.digittowordlearner.interfaces.DigitUpdateInterface;
import com.godavari.digittowordlearner.listeners.MyCustomNavigationItemSelected;
import com.godavari.digittowordlearner.listeners.MyCustomTextWatcher;
import com.godavari.digittowordlearner.listeners.MyCustomTouchListener;
import com.godavari.digittowordlearner.utility.LocaleLanguageHelper;
import com.godavari.digittowordlearner.utility.ShareUtility;
import com.godavari.lib.digittowords.DigitToWordUtility;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.text.NumberFormat;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements DigitUpdateInterface {

    private static final String LOG_CAT = MainActivity.class.getName();

    private TextInputEditText etDigits, etWords, etIndianStdDigits, etWordsEnglish;
    private BottomNavigationView bottomNavigation;

    private String currentLanguageSelected;
    private int currentDigit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etDigits = (TextInputEditText) findViewById(R.id.etDigits);
        etIndianStdDigits = (TextInputEditText) findViewById(R.id.etIndianStdDigits);
        etWords = (TextInputEditText) findViewById(R.id.etWords);
        etWordsEnglish = (TextInputEditText) findViewById(R.id.etWordsEnglish);

        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new MyCustomNavigationItemSelected(this));

        etDigits.setOnTouchListener(new MyCustomTouchListener(this));
        etIndianStdDigits.setOnTouchListener(new MyCustomTouchListener(this));
        etWords.setOnTouchListener(new MyCustomTouchListener(this));
        etWordsEnglish.setOnTouchListener(new MyCustomTouchListener(this));

        etDigits.addTextChangedListener(new MyCustomTextWatcher(this));

        etDigits.requestFocus();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleLanguageHelper.onAttach(base));
        currentLanguageSelected = LocaleLanguageHelper.getLanguage(this);
        DigitToWordUtility.reloadData(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        menuInflater.inflate(R.menu.localization_menu, menu);

        if (DigitToWordUtility.ENGLISH_LANGUAGE_LOCALE.equals(currentLanguageSelected)) {
            menu.findItem(R.id.miEnglish).setChecked(true);
        } else if (DigitToWordUtility.HINDI_LANGUAGE_LOCALE.equals(currentLanguageSelected)) {
            menu.findItem(R.id.miHindi).setChecked(true);
        } else if (DigitToWordUtility.MARATHI_LANGUAGE_LOCALE.equals(currentLanguageSelected)) {
            menu.findItem(R.id.miMarathi).setChecked(true);
        } else if (DigitToWordUtility.GUJARATI_LANGUAGE_LOCALE.equals(currentLanguageSelected)) {
            menu.findItem(R.id.miGujarati).setChecked(true);
        } else if (DigitToWordUtility.PUNJABI_LANGUAGE_LOCALE.equals(currentLanguageSelected)) {
            menu.findItem(R.id.miPunjabi).setChecked(true);
        } else if (DigitToWordUtility.BENGALI_LANGUAGE_LOCALE.equals(currentLanguageSelected)) {
            menu.findItem(R.id.miBengali).setChecked(true);
        } else if (DigitToWordUtility.KANNADA_LANGUAGE_LOCALE.equals(currentLanguageSelected)) {
            menu.findItem(R.id.miKannada).setChecked(true);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuItemId = item.getItemId();
        switch (menuItemId) {
            case R.id.miCopyResult:
                copyContent(toString());
                return true;
            case R.id.miShareResult:
                shareContent();
                return true;
            case R.id.miEnglish:
                item.setChecked(true);
                changeLanguage(DigitToWordUtility.ENGLISH_LANGUAGE_LOCALE);
                return true;
            case R.id.miHindi:
                item.setChecked(true);
                changeLanguage(DigitToWordUtility.HINDI_LANGUAGE_LOCALE);
                return true;
            case R.id.miMarathi:
                item.setChecked(true);
                changeLanguage(DigitToWordUtility.MARATHI_LANGUAGE_LOCALE);
                return true;
            case R.id.miGujarati:
                item.setChecked(true);
                changeLanguage(DigitToWordUtility.GUJARATI_LANGUAGE_LOCALE);
                return true;
            case R.id.miPunjabi:
                item.setChecked(true);
                changeLanguage(DigitToWordUtility.PUNJABI_LANGUAGE_LOCALE);
                return true;
            case R.id.miBengali:
                item.setChecked(true);
                changeLanguage(DigitToWordUtility.BENGALI_LANGUAGE_LOCALE);
                return true;
            case R.id.miKannada:
                item.setChecked(true);
                changeLanguage(DigitToWordUtility.KANNADA_LANGUAGE_LOCALE);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void digitUpdated(final int digit) {
        currentDigit = digit;
        if (digit != DigitToWordUtility.NOT_SUPPORTING_NUMBERS) {
            DigitToWordUtility.DigitToWordSubClass digitToWordSubClass = DigitToWordUtility.convertDigitsToWords(this, currentLanguageSelected, digit);
            final String words = digitToWordSubClass.digitToWordString;
            final String wordsEnglish = digitToWordSubClass.digitToWordEnglishString;
            Log.i(LOG_CAT, "Digits: " + digit + ", words: " + words + ", words english: " + wordsEnglish);
            etWords.postDelayed(new Runnable() {
                @Override
                public void run() {
                    etIndianStdDigits.setText(formatToCurrency(digit));
                    etWords.setText(words);
                    etWordsEnglish.setText(wordsEnglish);
                }
            }, 50);
        } else {
            etWords.postDelayed(new Runnable() {
                @Override
                public void run() {
                    etIndianStdDigits.setText(null);
                    etWords.setText(null);
                    etWordsEnglish.setText(null);
                }
            }, 50);
        }
    }

    @Override
    public void noOfDigit(int noOfDigit) {
        if (etDigits.getText().length() == 0) {
            etDigits.setError(null);
            digitUpdated(DigitToWordUtility.NOT_SUPPORTING_NUMBERS);
        }
        if (etDigits.getText().length() > DigitToWordUtility.NO_OF_DIGIT_SUPPORTED) {
            etDigits.setError(getString(R.string.not_supported));
            digitUpdated(DigitToWordUtility.NOT_SUPPORTING_NUMBERS);

        } else {
            etDigits.setError(null);
        }
    }

    public static String formatToCurrency(int value) {
        try {
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
            return formatter.format(value);
        } catch (Exception e) {
            return "0";
        }
    }

    private void changeLanguage(String language) {
        currentLanguageSelected = language;
        LocaleLanguageHelper.setLocale(this, language);
        recreate();
    }

    private void shareContent() {
        if (TextUtils.isEmpty(etDigits.getText())) {
            createToast(R.string.digits_not_entered);
        } else {
            ShareUtility.shareTheContent(this, toString());
        }
    }

    private void copyContent(String copyContent) {
        int stringResource = -1;
        if (TextUtils.isEmpty(etDigits.getText())) {
            stringResource = R.string.digits_not_entered;
        } else {
            stringResource = R.string.copied;
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(getString(R.string.app_name), copyContent);
            clipboard.setPrimaryClip(clip);
        }
        createToast(stringResource);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getString(R.string.app_name) + " " + getString(R.string.app) + "\n")
                .append(getString(R.string.digits) + " - " + currentDigit + "\n")
                .append(getString(R.string.indian_formatter) + " - " + formatToCurrency(currentDigit) + "\n")
                .append(getString(R.string.words) + " - " + DigitToWordUtility.convertDigitsToWords(this, currentLanguageSelected, currentDigit).digitToWordString + "\n")
                .append(getString(R.string.words_in_english) + " - " + DigitToWordUtility.convertDigitsToWords(this, currentLanguageSelected, currentDigit).digitToWordEnglishString);
        return stringBuffer.toString();
    }

    private void createToast(int stringResource) {
        Toast.makeText(this, stringResource, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void rightDrawableClicked(int viewId) {
        switch (viewId) {
            case R.id.etDigits:
                etDigits.setText("");
                break;
            case R.id.etIndianStdDigits:
                copyContent(etIndianStdDigits.getText().toString());
                break;
            case R.id.etWords:
                copyContent(etWords.getText().toString());
                break;
            case R.id.etWordsEnglish:
                copyContent(etWordsEnglish.getText().toString());
                break;

        }
    }
}
