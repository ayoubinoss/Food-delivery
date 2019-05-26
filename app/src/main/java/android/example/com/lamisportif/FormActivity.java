package android.example.com.lamisportif;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.util.MapUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class FormActivity extends AppCompatActivity {

    private LinearLayout mParentLayout;
    private LinkedList<LinearLayout> mCardViews = new LinkedList<>();
    private LinkedList<TextView> mFields = new LinkedList<>();
    private Map<String, LinkedList<CheckBox>> map = new HashMap<>();

    public  int idCard = 0;
    public  int idField = 1000;
    public  int idCheckbox = 2000;

    public final String CARD = "card";
    public final String FIELD = "field";
    public final String CHECKBOX = "checkbox";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        mParentLayout = findViewById(R.id.parent_layout);

        for(int i = 0; i < 4; i++) {
            addCardView();
            addTextView("field : " + i, mCardViews.get(i));
            LinkedList<CheckBox> checkBoxes = new LinkedList<>();
            for(int j = 0; j < 3; j++) {
                checkBoxes.add(addCheckBox("check " + j, mCardViews.get(i)));
            }
            map.put("field : ", checkBoxes);
        }

        Log.d("id of the first card:", mCardViews.get(0).getId()+"");

    }

    /**
     * function to add a card view
     */
    public void addCardView() {

        CardView cardView = new CardView(this);
        /*linear layout*/
        LinearLayout linearLayout = new LinearLayout(this);
        setLinearLayoutAttribute(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        /*end*/
        linearLayout.setId(idCard);
        setCardViewAttribute(cardView);
        cardView.addView(linearLayout);
        mParentLayout.addView(cardView);
        mCardViews.add(linearLayout);
    }

    /**
     * function to set parameters of a linear layout
     * @param linearLayout layout inside cardView
     */
    private void setLinearLayoutAttribute(@NonNull LinearLayout linearLayout) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        linearLayout.setLayoutParams(params);

    }

    /**
     * function to add a CheckBox
     */

    public CheckBox addCheckBox(String text, LinearLayout linearLayout) {
        CheckBox checkBox = new CheckBox(this);
        checkBox.setText(text);
        checkBox.setId(idCheckbox);
        setCheckBoxAttribute(checkBox);
        linearLayout.addView(checkBox);

        return checkBox;
    }

    private void setCheckBoxAttribute(CheckBox checkBox) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 16, 16, 0);

        checkBox.setLayoutParams(params);
    }

    /**
     * function to set attribute for a cardView
     * @param cardView a CardView instance to set it's parameter
     */
    public void setCardViewAttribute(CardView cardView) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 16, 16, 0);
        cardView.setPadding(0,0,0,16);
        cardView.setLayoutParams(params);
    }

    /**
     * function to add a card view
     */
    public void addTextView(String text, LinearLayout linearLayout) {

        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setId(idField);
        setTextViewAttribute(textView);

        linearLayout.addView(textView);

        mFields.add(textView);

        idField++;
        idCard++;
    }

    /**
     * function to set attribute for a cardView
     * @param textView a textView instance to set it's parameter
     */
    public void setTextViewAttribute(TextView textView) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 16, 16, 0);

        textView.setLayoutParams(params);
    }

    public void createForm(HashMap <String, LinkedList<String>> fields, LinkedList<String> keys) {

        for(int i = 0; i < fields.size(); i++) {
            addCardView();
            addTextView(keys.get(i),mCardViews.get(i));
            LinkedList<CheckBox> checkBoxes = new LinkedList<>();
            for (int j = 0; j < fields.get(keys.get(i)).size(); j++) {
                checkBoxes.add(addCheckBox((fields.get(keys.get(i))).get(j), mCardViews.get(i)));
            }
            map.put(keys.get(i), checkBoxes);
        }
    }
    //todo form validation
    public void formValidation() {

    }

}
