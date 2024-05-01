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
    public boolean ConnectableThroughNorth(int position1,int position2,int numberOfColumns){
        int col1=position1%numberOfColumns;
        int col2=position2%numberOfColumns;
        int row1=position1/numberOfColumns;
        int row2=position2/numberOfColumns;
        for(int i=row1-1;i>=0;i--){
            if (!imageState.get((i*numberOfColumns)+col1)) {
                return false;
            }
            else continue;
        }
        for(int i=row2-1;i>=0;i--){
            if (!imageState.get((i*numberOfColumns)+col2)) {
                return false;
            }
            else continue;
        }
        return  true;
    }
    public boolean ConnectableThroughSouth(int position1,int position2,int numberOfColumns){
        int col1=position1%numberOfColumns;
        int col2=position2%numberOfColumns;
        int row1=position1/numberOfColumns;
        int row2=position2/numberOfColumns;
        for(int i=row1+1;i<=9;i++){
            if (!imageState.get((i*numberOfColumns)+col1)) {
                return false;
            }
            else continue;
        }
        for(int i=row2+1;i<=9;i++){
            if (!imageState.get((i*numberOfColumns)+col2)) {
                return false;
            }
            else continue;
        }
        return true;
    }
    public boolean ConnectableThroughWest(int position1,int position2,int numberOfColumns){
        int col1=position1%numberOfColumns;
        int col2=position2%numberOfColumns;
        int row1=position1/numberOfColumns;
        int row2=position2/numberOfColumns;
        for(int i=col1-1;i>=0;i--){
            if (!imageState.get((row1*numberOfColumns)+i)) {
                return false;
            }
            else continue;
        }
        for(int i=col2-1;i>=0;i--){
            if (!imageState.get((row2*numberOfColumns)+i)) {
                return false;
            }
            else continue;
        }
        return true;
    }
    public boolean ConnectableThroughEast(int position1,int position2,int numberOfColumns){
        int col1=position1%numberOfColumns;
        int col2=position2%numberOfColumns;
        int row1=position1/numberOfColumns;
        int row2=position2/numberOfColumns;
        for(int i=col1+1;i<=5;i++){
            if (!imageState.get((row1*numberOfColumns)+i)) {
                return false;
            }
            else continue;
        }
        for(int i=col2+1;i<=5;i++){
            if (!imageState.get((row2*numberOfColumns)+i)) {
                return false;
            }
            else continue;
        }
        return true;
    }
    public boolean ConnectableThroughBorder(int position1,int position2,int numberOfColumns){
        if(ConnectableThroughNorth(position1,position2,numberOfColumns)) return true;
        if(ConnectableThroughSouth(position1,position2,numberOfColumns)) return true;
        if(ConnectableThroughWest(position1,position2,numberOfColumns)) return true;
        if(ConnectableThroughEast(position1,position2,numberOfColumns)) return true;
        return false;
    }
    public static boolean areAllImagesMatched() {
        for (boolean state : imageState) {
            if (!state) {
                return false; // At least one image is not matched
            }
        }
        return true; // All images are matched
    }
}
