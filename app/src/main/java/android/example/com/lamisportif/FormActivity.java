package android.example.com.lamisportif;

import android.example.com.lamisportif.models.Meal;
import android.example.com.lamisportif.models.OrderLine;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.MapUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.rpc.Help;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class FormActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mParentLayout;
    private LinkedList<LinearLayout> mCardViews = new LinkedList<>();
    private LinkedList<TextView> mFields = new LinkedList<>();
    private Map<String, LinkedList<CheckBox>> map = new HashMap<>();
    /**
     * String : Question
     * RadioGroup : RadioGroup concerned
     */
    private Map<String, RadioGroup> mapRadio = new HashMap<>();

    /**
     * String : Question
     * String : Choice given by the user
     */
    private Map<String,String> mapAnswer = new HashMap<>();

    /**
     * Integer : RadioButton's id
     * String : Choice
     */
    private Map<Integer,String> mapButtons = new HashMap<>();

    /**
     * Integer : RadioGroup's id
     * Boolean : check status
     */
    private Map<Integer,Boolean> mapCheckedRadios = new HashMap<>();

    public  int idCard = 0;
    public  int idField = 1000;
    public  int idCheckbox = 2000;
    public int idRadioButton = 3000;
    public int idRadioGroup = 4000;

    public final String CARD = "card";
    public final String FIELD = "field";
    public final String CHECKBOX = "checkbox";


    private static final String TAG = " Restaurant Activity ";
    private static final String LABEL_COLLECTION = "restaurants";
    private static final String LABEL_COLLECTION_1 = "categories";
    private static final String LABEL_COLLECTION_2 = "meals";
    private static final String LABEL_COLLECTION_3 = "questions";
    private static final String LABEL_QUESTION = "question";
    private static final String LABEL_CHOICES = "choices";

    HashMap <String, LinkedList<String>> fields = new HashMap<>();
    LinkedList<String> keys = new LinkedList<>();

    Meal meal = new Meal();
    OrderLine orderLine = new OrderLine();
    TextView quantityView;
    TextView designationView;
    TextView totalView;
    ImageView addIcon;
    ImageView removeIcon;
    ImageView closeIcon;
    FloatingActionButton confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        mParentLayout = findViewById(R.id.parent_layout);

        //View
        quantityView = (TextView) findViewById(R.id.order_quantity);
        designationView = (TextView) findViewById(R.id.label_plat_);
        totalView = (TextView) findViewById(R.id.price_);
        addIcon = (ImageView) findViewById(R.id.add);
        removeIcon = (ImageView) findViewById(R.id.remove);
        closeIcon = (ImageView) findViewById(R.id.close_btn);
        confirmButton = (FloatingActionButton) findViewById(R.id.confirm_fields);

        // Listeners
        addIcon.setOnClickListener(this);
        removeIcon.setOnClickListener(this);
        closeIcon.setOnClickListener(this);
        confirmButton.setOnClickListener(this);
        confirmButton.setEnabled(false);

        // Intent & Bundle
        Bundle bundle = getIntent().getBundleExtra("bundle");
        meal.setCategoryID(bundle.getString("categoryID"));
        meal.setMealID(bundle.getString("mealID"));
        meal.setRestaurantID(bundle.getString("restaurantID"));
        orderLine.setPrice(bundle.getDouble("price"));
        orderLine.setDesignation(bundle.getString("designation"));
        orderLine.setQuantity(1);

        //View's Data
        designationView.setText(orderLine.getDesignation());
       updateValues();

       //get Data
        getFields(); // it does display the form as well
        Log.d(TAG,"Inside on Create : "+fields + "questions : "+keys);
        Log.d(TAG, " here here :" + mapRadio);

    }

    /**
     * Create radioButton
     * @param choice choice
     * @param radioGroup radioGroup root
     * @return
     */
    public void createRadioButton(String choice,RadioGroup radioGroup){
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText(choice);
        radioButton.setTextSize(18);
        mapButtons.put(idRadioButton,choice);
        radioButton.setId(idRadioButton++);
        setRadioButtonAttribute(radioButton);
        radioGroup.addView(radioButton);
    }

    private void setRadioButtonAttribute(RadioButton radioButton){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 16, 16, 0);

        radioButton.setLayoutParams(params);
    }
    /**
     * Create radioGroup
     * @param choices the choices given for a certain question
     * @param linearLayout layout inside cardView
     * @return
     */
    public RadioGroup createRadioGroup(LinkedList<String> choices, LinearLayout linearLayout){
        RadioGroup radioGroup = new RadioGroup(this);
        setLinearLayoutAttribute(radioGroup);
        radioGroup.setId(idRadioGroup);
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        for(String val : choices){
            createRadioButton(val,radioGroup);
        }
        mapCheckedRadios.put(idRadioGroup++,false);
        linearLayout.addView(radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mapCheckedRadios.put(group.getId(),true);
                if(isValid()){
                    confirmButton.setEnabled(true);
                }
            }
        });
        return radioGroup;
    }
    /**
     * Update the values of quantity & total values
     */
    public void updateValues(){
        quantityView.setText(String.valueOf(orderLine.getQuantity()).concat("x"));
        totalView.setText(String.valueOf(new DecimalFormat("#0.00").format(orderLine.getPrice() * orderLine.getQuantity())).concat(" MAD"));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add :
                if(orderLine.getQuantity() < 10) {
                    orderLine.setQuantity(orderLine.getQuantity() + 1);
                    updateValues();
                }
                break;
            case R.id.remove :
                if(orderLine.getQuantity() > 1){
                    orderLine.setQuantity(orderLine.getQuantity()-1);
                    updateValues();
                }
                if(orderLine.getQuantity() == 1 ){
                    finish();
                }
                break;
            case R.id.close_btn :
                finish();
                break;
            case R.id.confirm_fields :
                getAnswers();
                orderLine.setTotal(orderLine.getPrice() * orderLine.getQuantity());
                orderLine.setMapAnswer(mapAnswer);
                Log.d(TAG,"orderLine :" + orderLine.toString());
                break;

        }

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
        textView.setTextSize(20);
        textView.setLayoutParams(params);
    }

    /**
     * function to create a form
     *  @param fields : a map that contain the choices for a certain question. key : question => value :[choice 1, choice 2, choice 3...]
     *  @param keys : the questions
     */
    public void createFormRadio( HashMap <String, LinkedList<String>> fields,LinkedList<String> keys) {

        for(int i = 0; i < fields.size(); i++) {
            addCardView();
            addTextView(keys.get(i),mCardViews.get(i));
            mapRadio.put(keys.get(i),createRadioGroup(fields.get(keys.get(i)),mCardViews.get(i)));
        }
    }
    /**
     * function to create a form
     *  @param fields : a map that contain the choices for a certain question. key : question => value :[choice 1, choice 2, choice 3...]
     *  @param keys : the questions
     */
    public void createForm( HashMap <String, LinkedList<String>> fields,LinkedList<String> keys) {

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

    /**
     *   if all the RadioGroup's are checked : True , else : false
     * @return
     */
    public boolean isValid() {
        for(Integer key : mapCheckedRadios.keySet()){
            if(!mapCheckedRadios.get(key).equals(Boolean.TRUE)){
                return false;
            }
        }
        return true;
    }
    public void getFields(){
        keys.clear();
        fields.clear();
        FirebaseFirestore.getInstance()
                .collection(LABEL_COLLECTION)
                .document(meal.getRestaurantID())
                .collection(LABEL_COLLECTION_1)
                .document(meal.getCategoryID())
                .collection(LABEL_COLLECTION_2)
                .document(meal.getMealID())
                .collection(LABEL_COLLECTION_3)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG,"Data : " + document.getData());
                                String question = (String) document.get(LABEL_QUESTION);
                                ArrayList<String> choicesArray = new ArrayList<>();
                                choicesArray = (ArrayList) document.get(LABEL_CHOICES);
                                LinkedList<String> choicesList = new LinkedList<>();
                                //parsing
                                for(String val : choicesArray){
                                    choicesList.add(val);
                                }
                                //add
                                keys.add(question);
                                fields.put(question,choicesList);
                                Log.d(TAG,"here => " + fields);
                            }
                            createFormRadio(fields,keys);

                        }
                    }
                });
    }

    public void getAnswers(){
        for(int i = 0; i<idCard;i++){
            RadioGroup  radioGroup = mapRadio.get(keys.get(i));
            int selectedId = radioGroup.getCheckedRadioButtonId();
            mapAnswer.put(keys.get(i),mapButtons.get(selectedId));
        }
    }
}
