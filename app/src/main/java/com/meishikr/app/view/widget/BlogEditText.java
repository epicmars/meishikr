//package com.meishikr.app.view.widget;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//import android.text.SpannableStringBuilder;
//import android.text.Spanned;
//import android.text.TextPaint;
//import android.text.style.ImageSpan;
//import android.util.AttributeSet;
//import android.widget.EditText;
//
//import com.meishikr.app.R;
//import com.meishikr.app.view.activity.blog.BlogEditActivity;
//import com.sin2pi.brick.common.utils.StringUtil;
//import com.sin2pi.brick.components.gallery.ImageFileEntry;
//import com.sin2pi.brick.components.utils.ImageUtil;
//
//import java.io.File;
//import java.util.List;
//
///**
// *
// */
//public class BlogEditText extends EditText implements BlogEditActivity.OnImageSelectListener {
//
//    private static final String LINE_FEED = "\n";
//    private String mExampleString; // TODO: use a default from R.string...
//    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
//    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
//    private Drawable mExampleDrawable;
//
//    private TextPaint mTextPaint;
//    private float mTextWidth;
//    private float mTextHeight;
//
//    private int contentWidth, contentHeight;
//    private Context context;
//
//    public BlogEditText(Context context) {
//        super(context);
//        init(context, null, 0);
//    }
//
//    public BlogEditText(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(context, attrs, 0);
//    }
//
//    public BlogEditText(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        init(context, attrs, defStyle);
//    }
//
//    private void init(Context context, AttributeSet attrs, int defStyle) {
//        this.context = context;
//        // Load attributes
//        final TypedArray a = getContext().obtainStyledAttributes(
//                attrs, R.styleable.BlogEditText, defStyle, 0);
//
//        mExampleString = a.getString(
//                R.styleable.BlogEditText_exampleString);
//        mExampleColor = a.getColor(
//                R.styleable.BlogEditText_exampleColor,
//                mExampleColor);
//        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
//        // values that should fall on pixel boundaries.
//        mExampleDimension = a.getDimension(
//                R.styleable.BlogEditText_exampleDimension,
//                mExampleDimension);
//
//        if (a.hasValue(R.styleable.BlogEditText_exampleDrawable)) {
//            mExampleDrawable = a.getDrawable(
//                    R.styleable.BlogEditText_exampleDrawable);
//            mExampleDrawable.setCallback(this);
//        }
//
//        a.recycle();
//
//        // Set up a default TextPaint object
//        mTextPaint = new TextPaint();
//        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
//        mTextPaint.setTextAlign(Paint.Align.LEFT);
//
//        // Update TextPaint and text measurements from attributes
//        invalidateTextPaintAndMeasurements();
//    }
//
//    private void invalidateTextPaintAndMeasurements() {
//        mTextPaint.setTextSize(mExampleDimension);
//        mTextPaint.setColor(mExampleColor);
//        mTextWidth = mTextPaint.measureText(mExampleString);
//
//        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
//        mTextHeight = fontMetrics.bottom;
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        // TODO: consider storing these as member variables to reduce
//        // allocations per draw cycle.
//        int paddingLeft = getPaddingLeft();
//        int paddingTop = getPaddingTop();
//        int paddingRight = getPaddingRight();
//        int paddingBottom = getPaddingBottom();
//
//        contentWidth = getWidth() - paddingLeft - paddingRight;
//        contentHeight = getHeight() - paddingTop - paddingBottom;
//
//        // Draw the text.
//        canvas.drawText(mExampleString,
//                paddingLeft + (contentWidth - mTextWidth) / 2,
//                paddingTop + (contentHeight + mTextHeight) / 2,
//                mTextPaint);
//
//        // Draw the example drawable on top of the text.
////        if (mExampleDrawable != null) {
////            mExampleDrawable.setBounds(paddingLeft, paddingTop,
////                    paddingLeft + contentWidth, paddingTop + contentHeight);
////            mExampleDrawable.draw(canvas);
////        }
//    }
//
//    /**
//     * Gets the example string attribute value.
//     *
//     * @return The example string attribute value.
//     */
//    public String getExampleString() {
//        return mExampleString;
//    }
//
//    /**
//     * Sets the view's example string attribute value. In the example view, this string
//     * is the text to draw.
//     *
//     * @param exampleString The example string attribute value to use.
//     */
//    public void setExampleString(String exampleString) {
//        mExampleString = exampleString;
//        invalidateTextPaintAndMeasurements();
//    }
//
//    /**
//     * Gets the example color attribute value.
//     *
//     * @return The example color attribute value.
//     */
//    public int getExampleColor() {
//        return mExampleColor;
//    }
//
//    /**
//     * Sets the view's example color attribute value. In the example view, this color
//     * is the font color.
//     *
//     * @param exampleColor The example color attribute value to use.
//     */
//    public void setExampleColor(int exampleColor) {
//        mExampleColor = exampleColor;
//        invalidateTextPaintAndMeasurements();
//    }
//
//    /**
//     * Gets the example dimension attribute value.
//     *
//     * @return The example dimension attribute value.
//     */
//    public float getExampleDimension() {
//        return mExampleDimension;
//    }
//
//    /**
//     * Sets the view's example dimension attribute value. In the example view, this dimension
//     * is the font size.
//     *
//     * @param exampleDimension The example dimension attribute value to use.
//     */
//    public void setExampleDimension(float exampleDimension) {
//        mExampleDimension = exampleDimension;
//        invalidateTextPaintAndMeasurements();
//    }
//
//    /**
//     * Gets the example drawable attribute value.
//     *
//     * @return The example drawable attribute value.
//     */
//    public Drawable getExampleDrawable() {
//        return mExampleDrawable;
//    }
//
//    /**
//     * Sets the view's example drawable attribute value. In the example view, this drawable is
//     * drawn above the text.
//     *
//     * @param exampleDrawable The example drawable attribute value to use.
//     */
//    public void setExampleDrawable(Drawable exampleDrawable) {
//        mExampleDrawable = exampleDrawable;
//    }
//
//    @Override
//    public void onPhotoSelected(List<ImageFileEntry> entries) {
//        for(ImageFileEntry entry : entries) {
//            String path = entry.getFile().getPath();
//            if(null == path)
//                return;
//            File file = new File(path);
//            if(!file.exists())
//                return;
//            // 如内容不为空则换行
//            insertLineFeed();
//            // 插入图像uri
//            Uri fileUri = Uri.fromFile(file);
//            String imgPath = fileUri.toString();
//            //        mExampleDrawable = new BitmapDrawable(path);
//            //        invalidate();
//            int selectionStart = getSelectionStart();
//            Bitmap bitmap = ImageUtil.resizeImage(path, contentWidth, contentHeight);
//            BitmapDrawable photo = new BitmapDrawable(context.getResources(), bitmap);
//            photo.setBounds(0, 0, photo.getIntrinsicWidth(), photo.getIntrinsicHeight());
//            ImageSpan imageSpan = new ImageSpan(photo);
//            getText().insert(selectionStart, imgPath);
//            int selectionEnd = selectionStart + imgPath.length();
//            SpannableStringBuilder ssb = new SpannableStringBuilder(getText());
//            ssb.setSpan(imageSpan, selectionStart, selectionEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            setText(ssb);
//            setSelection(selectionEnd);
//        }
//
//    }
//
//    /**
//     * 在光标位置插入换行符
//     */
//    private void insertLineFeed(){
//        final String content = getText().toString();
//        if(StringUtil.isEmpty(content))
//            return;
//        // 光标之前无换行符则插入换行符
//        int selectionStart = getSelectionStart();
//        if(LINE_FEED.equals(content.substring(selectionStart - 1, selectionStart)))
//            return;
//        getText().insert(selectionStart, LINE_FEED);
//    }
//
//}
