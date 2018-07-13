package linkersoft.blackpanther.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Html;
import android.util.AttributeSet;

import java.io.IOException;


public class ColorEditText extends SoftEditText {



        public String textstatement, colorstatement,pureString;
        public ColorEditText(Context context, AttributeSet attrs) throws IOException {
            super(context, attrs);
            In(context,attrs);
        }
        public ColorEditText(Context context, AttributeSet attrs, int defStyleAttr)throws IOException {
            super(context, attrs, defStyleAttr);
            In(context,attrs);
        }

        String ColoredhtmlString(String text, String color){
            return "<font color="+color+">"+text+"</font>";
        }
        String boldhtmlString(String text){
            return "<b>"+text+"</b>";
        }

        public void setText (String textStatement, String colorStatement, boolean textIsHint){
            if(textStatement==null||colorStatement==null)return;
            String words[]=textStatement.split("#"),colors[]=colorStatement.split(":"),coloredSentence="",wrd;
            if(words.length!=colors.length)throw new IllegalStateException("color.length!=words.length");
            for (int i = 0; i < words.length; i++){
                wrd=ColoredhtmlString(words[i],colors[i]);
                if(wrd.contains("}")) wrd=wrd.replace('}','#');
                if(wrd.contains("%")) wrd=boldhtmlString(wrd.split("%")[0]);
                coloredSentence+=wrd;
            }if(textIsHint)setHint(Html.fromHtml(coloredSentence)); else setText(Html.fromHtml(coloredSentence));
            pureString=""+getText();
        }
        void In(Context context, AttributeSet attrs){
            boolean txtIsHint;
            TypedArray _attrs = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ColorEditText, 0, 0);
            try{
                textstatement =_attrs.getString(R.styleable.ColorEditText_Sentence);
                colorstatement =_attrs.getString(R.styleable.ColorEditText_Colours);
                txtIsHint=_attrs.getBoolean(R.styleable.ColorEditText_TextIsHint,false);
            }finally{_attrs.recycle();}
            setText(textstatement,colorstatement,txtIsHint);
        }

        @Override
        public boolean onTextContextMenuItem(int id){
        return false;
    }



    }



