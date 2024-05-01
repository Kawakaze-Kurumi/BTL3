package com.example.btl3;

import static android.widget.Toast.makeText;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl3.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    public int[] image ={R.drawable.image1, R.drawable.image3, R.drawable.image4,
            R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image2,
            R.drawable.image8, R.drawable.image9, R.drawable.image10, R.drawable.image11
            , R.drawable.image12, R.drawable.image13, R.drawable.image14, R.drawable.image15};

    public int previousClickedPosition = -1;
    public int numberOfColumns = 6;
    public int NumberOfClick = 0;
    public TextView timer;
    public Dialog dialog;
    public Button dialogtieptuc, dialogthoat;
    public void checkAllImagesMatched() {
        if (ImageAdapter.areAllImagesMatched()) {
            // Display your dialog here
            showDialog();
        }
    }
    public void showDialog2() {
        // For example:
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("THẬT ĐÁNG TIẾC");
        builder.setMessage("BẠN ĐÃ KHÔNG HOÀN THÀNH TRÒ CHƠI");
        builder.setPositiveButton("THOÁT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("CHƠI LẠI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void showDialog() {
        // Display your dialog code here
        // For example:
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("XIN CHÚC MỪNG");
        builder.setMessage("BẠN ĐÃ HOÀN THÀNH TRÒ CHƠI MỘT CÁCH XUẤT SẮC");
        builder.setPositiveButton("THOÁT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("MÀN TIẾP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Modify onItemClick method to call checkAllImagesMatched() after each click

    @SuppressLint({"UseCompatLoadingForDrawables", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = findViewById(R.id.countdown_timer);
        new CountDownTimer(6000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished/1000));
            }
            @Override
            public void onFinish() {
                showDialog2();
            }
        }.start();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        int repeatCount = 2;
        List<Integer> imageList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            int imageIndex = random.nextInt(image.length);
            for (int j = 0; j < repeatCount; j++) {
                imageList.add(image[imageIndex]);
            }
        }
        for(int i=0; i<100;i++){
            Collections.shuffle(imageList);
        }
        ImageAdapter imageAdapter = new ImageAdapter(MainActivity.this,imageList);
        binding.gridView.setAdapter(imageAdapter);

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_box);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_box));
        dialog.setCancelable(false);
        dialogtieptuc = dialog.findViewById(R.id.btnDialogTieptuc);
        dialogthoat = dialog.findViewById(R.id.btnDialogThoat);
        dialogtieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });


        binding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int column = position % numberOfColumns;
                int row = position / numberOfColumns;

                if (previousClickedPosition != -1 && previousClickedPosition != position) {
                     //Lấy số cột của vị trí trước đó
                    int previousColumn = previousClickedPosition % numberOfColumns;
                    int previousRow = previousClickedPosition / numberOfColumns;

                    // Kiểm tra xem cả hai vị trí có cùng nằm trên cùng một hàng thẳng hay không
                    boolean column2 = false;
                    boolean column3 = false;
                    boolean row2 = false;
                    boolean row3 = false;
                    boolean isHorizontalNeighbor = (row == previousRow) && (Math.abs(column - previousColumn) == 1);
                    boolean isVerticalNeighbor = (column == previousColumn) && (Math.abs(row - previousRow) == 1);
                    boolean isSameRow = row == previousRow;
                    boolean isSameColumn = column == previousColumn;

                    if(column == 0 && previousColumn ==0){
                        column2 =true;
                    }
                    if(column == 5 && previousColumn ==5){
                        column3 =true;
                    }
                    if(row == 0 && previousRow ==0){
                        row2 =true;
                    }
                    if(row == 9 && previousRow ==9){
                        row3 =true;
                    }
                    if (isSameRow) {
                        // Kiểm tra xem ảnh giữa hai ảnh có phải là ảnh rỗng hay không
                        boolean areMiddleImagesEmpty = imageAdapter.areMiddleImagesEmpty(previousClickedPosition, position);
                        boolean isImagesMatched=imageAdapter.isImagesMatched(previousClickedPosition, position);
                        // Nếu tất cả các ảnh giữa là ảnh rỗng, thì hai ảnh được chọn thành ảnh rỗng
                        if (areMiddleImagesEmpty&&isImagesMatched) {
                            imageAdapter.setImageState(previousClickedPosition, true);
                            imageAdapter.setImageState(position, true);
                        }
                    }
                    if (isSameColumn) {
                        // Kiểm tra xem tất cả các ảnh nằm trên cùng một cột giữa hai ảnh có phải là ảnh rỗng hay không
                        boolean areMiddleImagesEmpty = imageAdapter.areMiddleImagesEmpty2(previousClickedPosition, position, numberOfColumns);
                        boolean isImagesMatched=imageAdapter.isImagesMatched(previousClickedPosition, position);
                        // Nếu tất cả các ảnh là ảnh rỗng, thì hai ảnh được chọn thành ảnh rỗng
                        if (areMiddleImagesEmpty&&isImagesMatched) {
                            imageAdapter.setImageState(previousClickedPosition, true);
                            imageAdapter.setImageState(position, true);
                        }
                    }
                    if(imageAdapter.ConnectableThroughBorder(previousClickedPosition,position,numberOfColumns)){
                        if (imageAdapter.isImagesMatched(previousClickedPosition, position)) {
                            imageAdapter.setImageState(previousClickedPosition, true);
                            imageAdapter.setImageState(position, true);
                        }
                    }
                    if ( (column == previousColumn && column2)||(column == previousColumn && column3)
                            || (row == previousRow && row2) || (row == previousRow && row3)
                    || isHorizontalNeighbor || isVerticalNeighbor) {
                        // Hai vị trí nằm trên cùng một hàng thẳng, thực hiện xử lý
                        if (imageAdapter.isImagesMatched(previousClickedPosition, position)) {
                            // Cập nhật trạng thái của cả hai hình ảnh thành hình ảnh trống
                            imageAdapter.setImageState(previousClickedPosition, true);
                            imageAdapter.setImageState(position, true);
                        }
                    }
                    checkAllImagesMatched();
                }

                // Lưu vị trí của hình ảnh hiện tại để sử dụng cho lần nhấp tiếp theo
                previousClickedPosition = position;
                NumberOfClick++;
                if(NumberOfClick>1){
                    previousClickedPosition=-1;
                    NumberOfClick=0;
                }
            }
        });
    }
    public void TamDung(View view){
        dialog.show();
    }


}