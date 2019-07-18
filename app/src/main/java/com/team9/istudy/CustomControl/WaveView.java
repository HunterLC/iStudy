package com.team9.istudy.CustomControl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class WaveView extends View {
    /**
     * 自定义view
     */
        private int mHeight;
        private int mWidth;

        private Paint mPaint;

        private int i = 0;

        public WaveView(Context context) {
            this(context, null);
        }

        public WaveView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            initView(context, attrs);
        }

        private void initView(Context context, AttributeSet attrs) {
            mPaint = new Paint();
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setAntiAlias(true);

            waveBeanList.add( new WaveBean(5, 25, 30, (float) Math.PI / 3, Color.parseColor("#80FFFFFF"), Color.parseColor("#E6FFFFFF")));
            waveBeanList.add(new WaveBean(5, 20, 24, (float) Math.PI / 6, Color.parseColor("#A0FFFFFF"), Color.parseColor("#40ffffff")));
            waveBeanList.add(new WaveBean(5, 15, 16, (float) Math.PI / 12, Color.parseColor("#FFFFFFFF"), Color.parseColor("#E6FFFFFF")));
        }

        private List<WaveBean> waveBeanList = new ArrayList<>();

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            mWidth = w;
            mHeight = h;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            List<Path> pathList = new ArrayList<>();
            for (WaveBean waveBean : waveBeanList) {
                pathList.add(getWavePath(waveBean));
            }

            postDelayed(new Runnable() {

                @Override
                public void run() {
                    i = 0;
                    for (WaveBean waveBean : waveBeanList) {
                        waveBean.setAngle(waveBean.getAngle() + (float) Math.PI / waveBean.getSpeed());
                    }
                    postInvalidate();
                }
            }, 17);

            for (int i = 0; i < pathList.size(); i++) {
                drawPath(canvas, waveBeanList.get(i), pathList.get(i));
            }
        }

        private Path getWavePath(WaveBean waveBean) {
            Path path = new Path();
            //计算波纹绘制区间的开始Y坐标：level:使波纹具备一定深度（level - waveHeight/2）
            //开始的X坐标是最左边
            //结束的X,Y坐标在右下角
            float startY = mHeight - waveBean.getLevel() - waveBean.getWaveHeight() / 2;
            for (i = 0; i < mWidth; i += 5) {
                float y = (float) (startY - waveBean.getWaveHeight() * Math.sin(i * (2 * Math.PI / mWidth) + waveBean.getAngle()));

                if (i == 0) {
                    //设置path的起点
                    path.moveTo(0, y);
                } else {
                    //连线
                    path.lineTo(i, y);
                }
            }
            //封闭 路径
            path.lineTo(mWidth, mHeight);
            path.lineTo(0, mHeight);
            path.close();

            return path;
        }

        private void drawPath(Canvas canvas, WaveBean waveBean, Path path) {
            //计算渐变色区间的开始Y坐标,即波纹的开始Y坐标
            //开始的X坐标是最左边
            //结束的X,Y坐标在右下角
            float startY = mHeight - waveBean.getLevel() - waveBean.getWaveHeight() / 2;
            mPaint.setColor(waveBean.getStartColor());
            canvas.drawPath(path, mPaint);
        }
}
