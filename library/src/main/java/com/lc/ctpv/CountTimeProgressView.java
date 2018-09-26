package com.lc.ctpv;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by lichao on 2018/5/23.
 */

public class CountTimeProgressView extends View{
    private Context mContext;
    //小球运动轨迹
    private Path mBoardPath = new Path();
    private Path mSportPath = new Path();

    //背景色画笔
    private Paint mBoarderBottomPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //绘制画笔
    private Paint mBoarderDrawPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //标记的小球
    private Paint mMarkBallPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //背景色
    private Paint mBgPaint = new Paint();
    private Paint mTextPaint = new Paint();

    private PathMeasure mPathMeasure = new PathMeasure();
    private ValueAnimator mAnimator;

    private float[] mSportPos = new float[2];
    private float[] mSportTan = new float[2];

    private float mCurrentValue;
    private float mLength;

    private boolean markBallFlag = true;
    private float markBallWidth;
    private int markBallColor = Color.RED;
    private float boaderWidth;
    private int boaderDrawColor;
    private int boaderBottomColor;
    private int backgroundColorCenter;
    private String titleCenterText;
    private float titleCenterTextSize;
    private int titleCenterTextColor;
    private int countTime;
    private float startAngle;
    private boolean clockwise = true;
    private float radius;
    private float centerPaintX;
    private float centerPaintY;
    private boolean onAnimationCancelMark = false;
    private String displayText;
    private OnEndListener mOnEndListener;
    private TextStyle mTextStyle = TextStyle.JUMP;
    private boolean isRunning;
    private long overageTime;

    private static String TAG = "CountTimeProgressView";
    private static int DEFAULT_BACKGROUND_COLOR_CENTER = Color.parseColor("#00BCD4");
    private static float DEFAULT_BORDER_WIDTH = 3f;
    private static int DEFAULT_BORDER_DRAW_COLOR =  Color.parseColor("#4dd0e1");
    private static int DEFAULT_BORDER_BOTTOM_COLOR =  Color.parseColor("#D32F2F");
    private static float DEFAULT_MARK_BALL_WIDTH = 6f;

    private static int DEFAULT_MARK_BALL_COLOR =  Color.parseColor("#536DFE");
    private static boolean DEFAULT_MARK_BALL_FLAG =  true;
    private static float DEFAULT_START_ANGLE = 0f;
    private static boolean DEFAULT_CLOCKWISE = true;
    private static int DEFAULT_COUNT_TIME = 5;

    private static TextStyle DEFAULT_TEXTSTYLE = TextStyle.JUMP;
    private static String DEFAULT_TITLE_CENTER_TEXT = "jump";
    private static int DEFAULT_TITLE_CENTER_COLOR = Color.parseColor("#FFFFFF");
    private static float DEFAULT_TITLE_CENTER_SIZE = 16f;
    Paint mPaint;
    Path mPath;

    public CountTimeProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        mPaint = new Paint();

        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.CountTimeProgressView, 0, 0);
        if(attr != null){
            titleCenterTextSize = attr.getDimension(R.styleable.CountTimeProgressView_titleCenterSize, spToPx(DEFAULT_TITLE_CENTER_SIZE));
            titleCenterTextColor = attr.getColor(R.styleable.CountTimeProgressView_titleCenterColor, DEFAULT_TITLE_CENTER_COLOR);
            String titleCenterText = attr.getString(R.styleable.CountTimeProgressView_titleCenterText);
            titleCenterText = (titleCenterText==null) ? DEFAULT_TITLE_CENTER_TEXT : titleCenterText;
            boaderWidth = attr.getDimension(R.styleable.CountTimeProgressView_borderWidth, dpToPx(DEFAULT_BORDER_WIDTH));
            boaderDrawColor = attr.getColor(R.styleable.CountTimeProgressView_borderDrawColor, DEFAULT_BORDER_DRAW_COLOR);
            boaderBottomColor = attr.getColor(R.styleable.CountTimeProgressView_borderBottomColor, DEFAULT_BORDER_BOTTOM_COLOR);
            markBallWidth = attr.getDimension(R.styleable.CountTimeProgressView_markBallWidth, dpToPx(DEFAULT_MARK_BALL_WIDTH));
            markBallColor = attr.getColor(R.styleable.CountTimeProgressView_markBallColor, DEFAULT_MARK_BALL_COLOR);
            markBallFlag = attr.getBoolean(R.styleable.CountTimeProgressView_markBallFlag, DEFAULT_MARK_BALL_FLAG);
            backgroundColorCenter = attr.getColor(R.styleable.CountTimeProgressView_backgroundColorCenter, DEFAULT_BACKGROUND_COLOR_CENTER);
            startAngle = attr.getFloat(R.styleable.CountTimeProgressView_startAngle, DEFAULT_START_ANGLE);
            clockwise = attr.getBoolean(R.styleable.CountTimeProgressView_clockwise, DEFAULT_CLOCKWISE);
            int index = attr.getInteger(R.styleable.CountTimeProgressView_textStyle, DEFAULT_TEXTSTYLE.ordinal());
            mTextStyle = TextStyle.values()[index];
            countTime = attr.getInt(R.styleable.CountTimeProgressView_countTime, DEFAULT_COUNT_TIME);
            attr.recycle();
        }else{
            titleCenterTextSize = spToPx(DEFAULT_TITLE_CENTER_SIZE);
            titleCenterTextColor = DEFAULT_TITLE_CENTER_COLOR;
            titleCenterText = DEFAULT_TITLE_CENTER_TEXT;
            boaderWidth = dpToPx(DEFAULT_BORDER_WIDTH);
            boaderDrawColor = DEFAULT_BORDER_DRAW_COLOR;
            boaderBottomColor = DEFAULT_BORDER_BOTTOM_COLOR;
            markBallWidth = dpToPx(DEFAULT_MARK_BALL_WIDTH);
            markBallColor = DEFAULT_MARK_BALL_COLOR;
            markBallFlag = DEFAULT_MARK_BALL_FLAG;
            backgroundColorCenter = DEFAULT_BACKGROUND_COLOR_CENTER;
            startAngle = DEFAULT_START_ANGLE;
            clockwise = DEFAULT_CLOCKWISE;
            mTextStyle = DEFAULT_TEXTSTYLE;
            countTime = DEFAULT_COUNT_TIME;
        }

        radius = 90;
        centerPaintX = 100;
        centerPaintY = 100;
        startAngle = -90;
        mPath = new Path();
        mPath.addCircle(0f, 0f, radius, Path.Direction.CW);
        mPathMeasure.setPath(mPath, false);
        mLength = mPathMeasure.getLength();
        initAnimation();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(centerPaintX, centerPaintY);
        canvas.rotate(startAngle);

        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(0, 0, radius, mPaint);

        mPathMeasure.getPosTan(mCurrentValue * mLength, mSportPos, mSportTan);

        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mSportPos[0], mSportPos[1], 20, mPaint);
    }

    protected void initAnimation(){
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    private float spToPx(float sp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, mContext.getResources().getDisplayMetrics());
    }

    private float dpToPx(float dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mContext.getResources().getDisplayMetrics());
    }

    interface OnEndListener{
        public void onAnimationEnd();
        public void onClick(long overageTime);
    }

    enum TextStyle{
        JUMP, SECOND, CLOCK, NONE
    }
}
