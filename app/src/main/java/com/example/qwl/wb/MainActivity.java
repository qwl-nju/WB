package com.example.qwl.wb;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textView1;
    private Button btn_submit, btn_resume;
    private ImageView iv_canvas;
    private Bitmap baseBitmap;
    private Canvas canvas;
    private Paint paint;
    String totxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paint = new Paint();
        paint.setStrokeWidth(60);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        iv_canvas = (ImageView) findViewById(R.id.iv_canvas);
        btn_submit = (Button) findViewById(R.id.button_submit);
        btn_resume = (Button) findViewById(R.id.btn_resume);
        textView1 = (TextView)findViewById(R.id.textview);
        btn_submit.setOnClickListener(click);
        btn_resume.setOnClickListener(click);
        iv_canvas.setOnTouchListener(touch);




    }

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_submit:
                    submit();
                    break;
                case R.id.btn_resume:
                    resumeCanvas();
                    break;
                default:
                    break;
            }
        }
    };

    protected  void submit(){
        new Thread() {
            @Override
            public void run() {
                super.run();
                //baseBitmap=PicPro.smoothImage(baseBitmap);
                baseBitmap = Bitmap.createScaledBitmap(baseBitmap,28,28,false);

                totxt=PicPro.gray2Binary2txt(baseBitmap);


                String result = WebServiceOpra.people_name(totxt);
                Message msg = new Message();
                msg.what = 0x12;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }


            Handler mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    String restemp = msg.obj.toString();
                    String[] temp1=restemp.split("\\=");
                    String[] temp2=temp1[1].split("\\.");
                    textView1.setText(temp2[0]);

                }
            };
        }.start();
        //resumeCanvas();
        iv_canvas.setImageBitmap(baseBitmap);
        //Toast.makeText(this,totxt,Toast.LENGTH_SHORT).show();
    }

    protected void resumeCanvas() {
        // 手动清除画板的绘图，重新创建一个画板
        if (baseBitmap != null) {
            //baseBitmap = Bitmap.createBitmap(200,200, Bitmap.Config.ARGB_8888);
            baseBitmap = Bitmap.createBitmap(iv_canvas.getWidth(),
                    iv_canvas.getWidth(), Bitmap.Config.ARGB_8888);
            canvas = new Canvas(baseBitmap);
            canvas.drawColor(Color.WHITE);
            iv_canvas.setImageBitmap(baseBitmap);
            //Toast.makeText(MainActivity.this, "清除画板成功，可以重新开始绘图", 0).show();
        }
    }


    private View.OnTouchListener touch = new View.OnTouchListener() {
        // 定义手指开始触摸的坐标
        float startX;
        float startY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                // 用户按下动作
                case MotionEvent.ACTION_DOWN:
                    // 第一次绘图初始化内存图片，指定背景为白色
                    if (baseBitmap == null) {
                        baseBitmap = Bitmap.createBitmap(iv_canvas.getWidth(),
                                iv_canvas.getHeight(), Bitmap.Config.ARGB_8888);
                        canvas = new Canvas(baseBitmap);
                        canvas.drawColor(Color.WHITE);
                    }
                    // 记录开始触摸的点的坐标
                    startX = event.getX();
                    startY = event.getY();
                    break;
                // 用户手指在屏幕上移动的动作
                case MotionEvent.ACTION_MOVE:
                    // 记录移动位置的点的坐标
                    float stopX = event.getX();
                    float stopY = event.getY();

                    //根据两点坐标，绘制连线
                    canvas.drawLine(startX, startY, stopX, stopY, paint);

                    // 更新开始点的位置
                    startX = event.getX();
                    startY = event.getY();

                    // 把图片展示到ImageView中
                    iv_canvas.setImageBitmap(baseBitmap);
                    break;
                case MotionEvent.ACTION_UP:

                    break;
                default:
                    break;
            }
            return true;
        }
    };
}
