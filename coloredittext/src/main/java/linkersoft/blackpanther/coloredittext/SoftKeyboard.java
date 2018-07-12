package linkersoft.blackpanther.coloredittext;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by LiNKeR! on 12/30/2017.
 */

public class SoftKeyboard {

    static public boolean onKeyboarded;
    public static void dismissKeyboard(View view, Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService( Activity.INPUT_METHOD_SERVICE );
        imm.hideSoftInputFromWindow(view.getWindowToken(),0 );
    }
    public static void showKeyboard(View view, Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService( Activity.INPUT_METHOD_SERVICE );
        imm.showSoftInput( view, InputMethodManager.SHOW_IMPLICIT );

    }
    public static boolean isActive(){
        return onKeyboarded;
    }

    static keyboardListener kL;
    interface keyboardListener {
        void onKeyboardCall(float screenHeight, float keyboardHeight);
        void onKeyboardDismiss();
    }

    private static ViewTreeObserver vTo;
    private static ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
    public static void setImeOptions(EditText Ex, String Type){
        switch (Type){
            case "search":
                Ex.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                break;
            case "done":
                Ex.setImeOptions(EditorInfo.IME_ACTION_DONE);
                break;
            case "go":
                Ex.setImeOptions(EditorInfo.IME_ACTION_GO);
                break;
            case "next":
                Ex.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                break;
            case "email":
                Ex.setImeOptions(EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case "password":
                Ex.setImeOptions(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case "name":
                Ex.setImeOptions(EditorInfo.TYPE_TEXT_VARIATION_PERSON_NAME);
                break;
            case "number":
                Ex.setImeOptions(EditorInfo.TYPE_NUMBER_VARIATION_NORMAL);
                break;
            case "pin":
                Ex.setImeOptions(EditorInfo.TYPE_NUMBER_VARIATION_PASSWORD);
                break;
        }
        Ex.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
    }
    public static void setkeyboardListener(final View contentView, keyboardListener xkL) {
//        if(kL==null||activity==null)throw new NullPointerException("Activity||keyboardListener must !=null ");
//       call "activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);" In activity b4 calling this method(setKeyb...)
        kL = xkL;
        vTo=contentView.getViewTreeObserver();
        onGlobalLayoutListener=new ViewTreeObserver.OnGlobalLayoutListener() {
            float minTHRESH =.25f;
            @Override
            public void onGlobalLayout() {
                Rect rWinDisplay = new Rect();
                contentView.getWindowVisibleDisplayFrame(rWinDisplay);
                int scrnHght = contentView.getRootView().getHeight();
                float kbHght = scrnHght - rWinDisplay.bottom,
                        minkbHght=scrnHght * minTHRESH;
                if (kbHght > minkbHght){
                    onKeyboarded = true;
                    kL.onKeyboardCall(scrnHght,kbHght);
                } else{
                    onKeyboarded = false;
                    kL.onKeyboardDismiss();
                }//Log.i(venom.TAG,"   soft [ kbHght: "+kbHght+" minkbHght: "+minkbHght+" scrnHght: "+scrnHght+" rWinDisplay.bottomY: "+rWinDisplay.bottom+" onKeyboarded: "+onKeyboarded+" ]");
            }
        }; vTo.addOnGlobalLayoutListener(onGlobalLayoutListener);
    }
    public static void removekeyboardListener(){
        vTo.removeOnGlobalLayoutListener(onGlobalLayoutListener);
    }
}
