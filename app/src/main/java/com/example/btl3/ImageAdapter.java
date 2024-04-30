package com.example.btl3;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter {
    Context context;
    static List <Boolean> imageState;
    static List <Integer> image;
    LayoutInflater inflater;
    public int previousClickedPosition = -1;
    public int NumberOfItem =60;
    public ImageAdapter(Context context, List<Integer> image ){
        this.context = context;
        this.image = image;
        this.imageState = new ArrayList<>();
        // Khởi tạo danh sách trạng thái với giá trị mặc định là false (chưa được nhấp vào)
        for (int i = 0; i < image.size(); i++) {
            imageState.add(false);
        }
    }

    @Override
    public int getCount() {
        return image.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item, null);
        }
        ImageView imageView = convertView.findViewById(R.id.grid_item);
        imageView.setImageResource(image.get(position));

        // Thay đổi màu sắc của hình ảnh dựa trên trạng thái
        if (imageState.get(position)) {
            imageView.setImageResource(android.R.color.transparent);
        } else {
            imageView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }
    public void setImageState(int position, boolean state) {
        imageState.set(position, state);
        notifyDataSetChanged(); // Cập nhật lại giao diện người dùng
    }
    public boolean isImagesMatched(int position1, int position2) {
        return image.get(position1).equals(image.get(position2));
    }

    public void setPreviousClickedPosition(int position) {
        previousClickedPosition = position;
    }

    public int getPreviousClickedPosition() {
        return previousClickedPosition;
    }

    public boolean areMiddleImagesEmpty(int position1, int position2) {
        int start = Math.min(position1, position2) + 1;
        int end = Math.max(position1, position2) - 1;

        for (int i = start; i <= end; i++) {
            if (!imageState.get(i)) {
                return false; // Nếu có một ảnh giữa không phải là ảnh rỗng, trả về false
            }
        }
        return true; // Nếu tất cả các ảnh giữa là ảnh rỗng, trả về true
    }
    public boolean areMiddleImagesEmpty2(int position1, int position2, int numberOfColumns) {
        int startColumn = Math.min(position1 % numberOfColumns, position2 % numberOfColumns);
        int endColumn = Math.max(position1 % numberOfColumns, position2 % numberOfColumns);
        int startRow = Math.min(position1 / numberOfColumns, position2 / numberOfColumns) + 1;
        int endRow = Math.max(position1 / numberOfColumns, position2 / numberOfColumns) - 1;

        for (int column = startColumn; column <= endColumn; column++) {
            for (int row = startRow; row <= endRow; row++) {
                int position = row * numberOfColumns + column;
                if (!imageState.get(position)) {
                    return false; // Nếu có một ảnh không phải là ảnh rỗng, trả về false
                }
            }
        }
        return true; // Nếu tất cả các ảnh là ảnh rỗng, trả về true
    }
    public static boolean areAllImagesMatched() {
        for (boolean state : imageState) {
            if (!state) {
                return false; // At least one image is not matched
            }
        }
        return true; // All images are matched
    }

    //    public int[][] createMatrix(int position1, int position2, int numberOfColumns) {
//        // Tính toán hàng và cột của vị trí 1
//        int row1 = position1 / numberOfColumns;
//        int column1 = position1 % numberOfColumns;
//
//        // Tính toán hàng và cột của vị trí 2
//        int row2 = position2 / numberOfColumns;
//        int column2 = position2 % numberOfColumns;
//
//        // Xác định hàng và cột bắt đầu và kết thúc của ma trận
//        int startRow = Math.min(row1, row2);
//        int endRow = Math.max(row1, row2);
//        int startColumn = Math.min(column1, column2);
//        int endColumn = Math.max(column1, column2);
//
//        // Tạo ma trận có kích thước phù hợp
//        int numRows = endRow - startRow + 1;
//        int numColumns = endColumn - startColumn + 1;
//        int[][] matrix = new int[numRows][numColumns];
//
//        // Điền giá trị vào ma trận dựa trên trạng thái của hình ảnh
//        for (int i = startRow; i <= endRow; i++) {
//            for (int j = startColumn; j <= endColumn; j++) {
//                if ((i == row1 && j == column1) || (i == row2 && j == column2)) {
//                    matrix[i - startRow][j - startColumn] = 2; // Hình ảnh 1 hoặc 2
//                } else {
//                    int position = i * numberOfColumns + j;
//                    if (imageState.get(position)) {
//                        matrix[i - startRow][j - startColumn] = 0; // Không có hình ảnh
//                    } else {
//                        matrix[i - startRow][j - startColumn] = 1; // Có hình ảnh
//                    }
//                }
//            }
//        }
//
//        return matrix;
//    }
//    public boolean hasObstacleBetween(int[][] matrix, int row1, int col1, int row2, int col2) {
//        int dx = Math.abs(col2 - col1);
//        int dy = Math.abs(row2 - row1);
//        int sx = col1 < col2 ? 1 : -1;
//        int sy = row1 < row2 ? 1 : -1;
//        int err = dx - dy;
//        int x = col1;
//        int y = row1;
//
//        while (x != col2 || y != row2) {
//            if (matrix[y][x] == 1) {
//                return true; // Số 1 xuất hiện trên đường thẳng giữa hai điểm
//            }
//            int e2 = 2 * err;
//            if (e2 > -dy) {
//                err -= dy;
//                x += sx;
//            }
//            if (e2 < dx) {
//                err += dx;
//                y += sy;
//            }
//        }
//        return false; // Không có số 1 nào xuất hiện trên đường thẳng giữa hai điểm
//    }
}
