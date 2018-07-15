package linkersoft.blackpanther.text;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;



public class SoftEditText extends EditText {

    public SoftEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        in(context,attrs);
    }
    public SoftEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        in(context,attrs);
    }
    void in(Context context, AttributeSet attrs){
        TypedArray tar = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SoftEditText, 0, 0);
        try{panHeight =tar.getInt(R.styleable.SoftEditText_panHeight,-1);
            pannerId =tar.getResourceId(R.styleable.SoftEditText_pannerId,-1);
            if(panHeight!=-1)panHeight=dp2px(panHeight,context);
        }catch (Exception e){
        }finally{tar.recycle();}
         this.context=context;
    }
    public static int dp2px(float Xdp, Context context) {
        return Math.round(Xdp * context.getResources().getDisplayMetrics().density);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(panHeight!=-1){
            ContentView=((Activity) context).findViewById(android.R.id.content);
            if(pannerView==null){
                if(pannerId!=-1) {
                    setPanning(ContentView.findViewById(pannerId));
                } else setPanning( ((ViewGroup)getParent()));
            }else setPanning(pannerView);
        }
    }


    private void setPanning(final View rootPanner){
        bottomY =getAbsY() + getHeight();
        setkeyboardListener(new SoftKeyboard.keyboardListener() {
            float transY;

            @Override
            public void onKeyboardCall(float screenHeight,float keyboardHeight) {
                if(keyboardY==0) {
                    keyboardY = screenHeight - keyboardHeight;
                    transY = keyboardY - (bottomY + panHeight);
                    rootPanner.animate().translationY(transY).setDuration(500).setInterpolator(new FastOutSlowInInterpolator());//you wan2 make the edttxt panHeightpxls above the keyboard;
                    keyboardY=-1;
                }
            }

            @Override
            public void onKeyboardDismiss() {
                if(keyboardY==-1){
                    keyboardY=0;
                    rootPanner.animate().translationY(0).setDuration(500).setInterpolator(new FastOutSlowInInterpolator());
                }
            }
        });
    }
    private float getKeyboardY() {
        return keyboardY;
    }
    private int getAbsY(){
            int[] location=new int[2];
            getLocationInWindow(location);
            return location[1];
    }
    public void CallKeyboard(){
        if(!calledKeyboard){
            SoftKeyboard.showKeyboard(this,context);
            calledKeyboard=true;
        }
    }
    public void DismissKeyboard(){
        if(calledKeyboard){
            SoftKeyboard.dismissKeyboard(this,context);
            calledKeyboard=false;
        }
    }
    public void setPanner(int panHeight,int pannerId){
        this.pannerId=pannerId;
        this.panHeight=panHeight;
    }
    public void setPanner(int panHeight,View pannerView){
        this.pannerView=pannerView;
        this.panHeight=panHeight;
    }
    public void setkeyboardListener(SoftKeyboard.keyboardListener listener){
        SoftKeyboard.setkeyboardListener(ContentView, listener);
    }
    public void removekeyboardListener(){
        SoftKeyboard.removekeyboardListener();
    }

    private boolean calledKeyboard;
    private View ContentView,pannerView;
    private int panHeight,pannerId;
    private Context context;
    private float keyboardY;
    private float bottomY;

    @Override
    public String toString() {
        return "@LiNKeR(>_<)~"+super.toString();
    }

}

