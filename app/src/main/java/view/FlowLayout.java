package view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by �� on 2015/7/20.
 */
public class FlowLayout extends ViewGroup {

    //�������췽��ȫ��ת�Ƶ� ����3�������Ĺ�����
    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context) {
        this(context, null);
    }

    //�������
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //wrap_content
        int width = 0;
        int height = 0;

        //��¼ÿһ�еĿ�Ⱥ͸߶�
        int lineWidth = 0;
        int lineHeight = 0;

        //�õ��ڲ�Ԫ�صĸ���
        int cCount = getChildCount();

        for(int i = 0; i < cCount; i++){
            View child = getChildAt(i);

            //������view�Ŀ�͸�
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //�õ�LayoutParams �������Ҫ��֮�ı�ģ����� ��д������֮ǰ�������Ѿ�Ԥ�ȶ�����generateLayoutParams ��ķ���ֵ��MarginLayoutParams
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            //��viewռ�ݵĿ��
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            //��viewռ�ݵĸ߶�
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            //����
            if(lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight())
            {
                //�Աȵõ������
                width = Math.max(width, lineWidth);
                //����lineWidth
                lineWidth = childWidth;
                //��¼�и�
                height += lineHeight;
                lineHeight = childHeight;
            }
            else
            {
                //�����п�
                lineWidth += childWidth;
                //�õ���ǰ�����ĸ߶�
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //���һ���ؼ� ע�� cCountҪ���С���1��
            if(i == cCount - 1){
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }

        Log.e("TAG", "SizeWidth = " + sizeWidth);
        Log.e("TAG", "SizeHeight = " + sizeHeight);
        //wrap_content
//        if(modeWidth == MeasureSpec.AT_MOST)
//        {
//            setMeasuredDimension(width + getPaddingLeft() + getPaddingRight(), height + getPaddingTop()+ getPaddingBottom());
//        }
//        else
//        {
//            setMeasuredDimension(sizeWidth, sizeHeight);
//        }
        //��д wrap_content
        setMeasuredDimension(
                //
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop()+ getPaddingBottom()//
        );
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //�洢���е�View������Ϊ��λ��
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    //ÿһ�еĸ߶�
    private List<Integer> mLineHeight = new ArrayList<Integer>();


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b){


        mAllViews.clear();
        mLineHeight.clear();

        //��ǰViewGroup�Ŀ��
        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;

        List<View> lineViews = new ArrayList<View>();

        int cCount = getChildCount();

        for(int i = 0; i < cCount; i++){
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            //�����Ҫ����
            if(childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width)
            {
                //��¼LineHeight
                mLineHeight.add(lineHeight);;
                //��¼��ǰ�е�Views
                mAllViews.add(lineViews);

                //�������ǵ��п���и�
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                //�������ǵ�View����
                lineViews = new ArrayList<View>();

            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin; // +=
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);

            lineViews.add(child);
        }//for end

        //�������һ��
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        //������view��λ��
        int left = getPaddingLeft();
        int top = getPaddingTop();

        //����
        int lineNum = mAllViews.size();

        //ɨÿһ�У��ѵ�ǰ�е�Ԫ��װ��lineViews���飬�Ѹ߶ȼ�¼��lineHeight
        for(int i = 0; i< lineNum;i++)
        {
            //��ǰ�е�����View
            lineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);

            //�ڵ�ǰ�У�����ÿһ��child�� �趨��child ��λ�õȵ�
            for(int j = 0; j < lineViews.size(); j++)
            {
                View child = lineViews.get(j);
                //�ж�child��״̬�� ��û��ʾ���Ͳ������child
                if(child.getVisibility() == View.GONE)
                {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                //����ԭ�� leftChild
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc +child.getMeasuredHeight();

                //Ϊ��view���в���
                child.layout(lc, tc, rc, bc);

                //������ͬ����˵������ֻ�С��������Ŀ�ȡ��ǲ�ͬ�ģ����������Ҫ��left �ۼ�
                left += child.getMeasuredWidth() + lp.leftMargin+ lp.rightMargin;
            }

            //��������һ��ʱ�����á������ľ��롱�����ۼ�top
            left = getPaddingLeft();
            top += lineHeight;

        }


    }


    //�뵱ǰViewGroup��Ӧ��LayoutParams
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

}
