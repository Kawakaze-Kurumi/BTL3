package com.example.btl3;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.btl3.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    public int[] image ={R.drawable.image1, R.drawable.image3, R.drawable.image4,
            R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image2,
            R.drawable.image8, R.drawable.image9, R.drawable.image10, R.drawable.image11
            , R.drawable.image12, R.drawable.image13, R.drawable.image14, R.drawable.image15};

    public int previousClickedPosition = -1;
    public int numberOfColumns = 6;

    public int NumberOfClick =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        binding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int column = position % numberOfColumns;
                int row = position / numberOfColumns;

                if (previousClickedPosition != -1 && previousClickedPosition != position) {
                    // Lấy số cột của vị trí trước đó
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

                        // Nếu tất cả các ảnh giữa là ảnh rỗng, thì hai ảnh được chọn thành ảnh rỗng
                        if (areMiddleImagesEmpty) {
                            imageAdapter.setImageState(previousClickedPosition, true);
                            imageAdapter.setImageState(position, true);
                        }
                    }
                    if (isSameColumn) {
                        // Kiểm tra xem tất cả các ảnh nằm trên cùng một cột giữa hai ảnh có phải là ảnh rỗng hay không
                        boolean areMiddleImagesEmpty = imageAdapter.areMiddleImagesEmpty2(previousClickedPosition, position, numberOfColumns);

                        // Nếu tất cả các ảnh là ảnh rỗng, thì hai ảnh được chọn thành ảnh rỗng
                        if (areMiddleImagesEmpty) {
                            imageAdapter.setImageState(previousClickedPosition, true);
                            imageAdapter.setImageState(position, true);
                        }
                    }
                    if ( (column == previousColumn && column2)||(column == previousColumn && column3) || (row == previousRow && row2) || (row == previousRow && row3)
                    || isHorizontalNeighbor || isVerticalNeighbor) {
                        // Hai vị trí nằm trên cùng một hàng thẳng, thực hiện xử lý
                        if (imageAdapter.isImagesMatched(previousClickedPosition, position)) {
                            // Cập nhật trạng thái của cả hai hình ảnh thành hình ảnh trống
                            imageAdapter.setImageState(previousClickedPosition, true);
                            imageAdapter.setImageState(position, true);
                        }
                    }
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
}