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
    static List<Boolean> imageState;
    static List<Integer> image;
    LayoutInflater inflater;
    public int previousClickedPosition = -1;
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
        int column=position1%numberOfColumns;
        int startRow = Math.min(position1 / numberOfColumns, position2 / numberOfColumns) + 1;
        int endRow = Math.max(position1 / numberOfColumns, position2 / numberOfColumns) - 1;
        for (int row = startRow; row <= endRow; row++) {
            int position = row * numberOfColumns + column;
            if (!imageState.get(position)) {
                return false; // Nếu có một ảnh không phải là ảnh rỗng, trả về false
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
    public boolean CommonCol3Dashes(int position1,int position2,int numberOfColumns){
        int col1=position1%numberOfColumns;
        int col2=position2%numberOfColumns;
        int row1=position1/numberOfColumns;
        int row2=position2/numberOfColumns;
        boolean iscleared=false;
        boolean connection1=false;
        boolean connection2=false;
        for(int commoncol=0;commoncol<numberOfColumns;commoncol++){
            int start=Math.min(row1,row2);
            int end=Math.max(row1,row2);
            for(int i=start;i<=end;i++){
                if (!imageState.get((i*numberOfColumns)+commoncol)){
                    iscleared=false;
                    break;
                }
                else if(i==end) iscleared=true;
            }
            if(iscleared){
                int start1=Math.min(col1,commoncol)+1;
                int end1=Math.max(col1,commoncol)-1;
                int start2=Math.min(col2,commoncol)+1;
                int end2=Math.max(col2,commoncol)-1;
                if(start1>end1) connection1=true;
                else{
                    for(int i=start1;i<=end1;i++){
                        if (!imageState.get((row1*numberOfColumns)+i)){
                            connection1=false;
                            break;
                        }
                        else if(i==end1) connection1=true;
                    }
                }
                if(start2>end2) connection2=true;
                else{
                    for(int i=start2;i<=end2;i++){
                        if (!imageState.get((row2*numberOfColumns)+i)){
                            connection2=false;
                            break;
                        }
                        else if(i==end2) connection2=true;
                    }
                }
                if(connection1&&connection2) return true;
            }
        }
        return false;
    }
    public boolean CommonRow3Dashes(int position1,int position2,int numberOfColumns){
        int col1=position1%numberOfColumns;
        int col2=position2%numberOfColumns;
        int row1=position1/numberOfColumns;
        int row2=position2/numberOfColumns;
        boolean iscleared=false;
        boolean connection1=false;
        boolean connection2=false;
        for(int commonrow=0;commonrow<10;commonrow++){
            int start=Math.min(col1,col2);
            int end=Math.max(col1,col2);
            for(int i=start;i<=end;i++){
                if (!imageState.get((commonrow*numberOfColumns)+i)){
                    iscleared=false;
                    break;
                }
                else if(i==end) iscleared=true;
            }
            if(iscleared){
                int start1=Math.min(row1,commonrow)+1;
                int end1=Math.max(row1,commonrow)-1;
                int start2=Math.min(row2,commonrow)+1;
                int end2=Math.max(row2,commonrow)-1;
                if(start1>end1) connection1=true;
                else{
                    for(int i=start1;i<=end1;i++){
                        if (!imageState.get((i*numberOfColumns)+col1)){
                            connection1=false;
                            break;
                        }
                        else if(i==end1) connection1=true;
                    }
                }
                if(start2>end2) connection2=true;
                else{
                    for(int i=start2;i<=end2;i++){
                        if (!imageState.get((i*numberOfColumns)+col2)){
                            connection2=false;
                            break;
                        }
                        else if(i==end2) connection2=true;
                    }
                }
                if(connection1&&connection2) return true;
            }
        }
        return false;
    }
    public boolean ForwardSlash2Dashes(int position1,int position2,int numberOfColumns){
        boolean connection1=false;
        boolean connection2=false;
        int higherposition=Math.min(position1,position2);
        int lowerposition=Math.max(position1,position2);
        int higherpositioncol=higherposition%numberOfColumns;
        int lowerpositioncol=lowerposition%numberOfColumns;
        int higherpositionrow=higherposition/numberOfColumns;
        int lowerpositionrow=lowerposition/numberOfColumns;
        for(int i=lowerpositioncol;i<=higherpositioncol-1;i++){
            if (!imageState.get((higherpositionrow*numberOfColumns)+i)){
                connection1=false;
                break;
            }
            else if(i==higherpositioncol-1) connection1=true;
        }
        for(int i=higherpositionrow;i<=lowerpositionrow-1;i++){
            if (!imageState.get((i*numberOfColumns)+lowerpositioncol)){
                connection2=false;
                break;
            }
            else if(i==lowerpositionrow-1) connection2=true;
        }
        if(connection1&&connection2){
            return true;
        }
        for(int i=lowerpositioncol+1;i<=higherpositioncol;i++){
            if (!imageState.get((lowerpositionrow*numberOfColumns)+i)){
                connection1=false;
                break;
            }
            else if(i==higherpositioncol) connection1=true;
        }
        for(int i=higherpositionrow+1;i<=lowerpositionrow;i++){
            if (!imageState.get((i*numberOfColumns)+higherpositioncol)){
                connection2=false;
                break;
            }
            else if(i==lowerpositionrow) connection2=true;
        }
        if(connection1&&connection2){
            return true;
        }
        return false;
    }
    public boolean BackwardSlash2Dashes(int position1,int position2,int numberOfColumns){
        boolean connection1=false;
        boolean connection2=false;
        int higherposition=Math.min(position1,position2);
        int lowerposition=Math.max(position1,position2);
        int higherpositioncol=higherposition%numberOfColumns;
        int lowerpositioncol=lowerposition%numberOfColumns;
        int higherpositionrow=higherposition/numberOfColumns;
        int lowerpositionrow=lowerposition/numberOfColumns;
        for(int i=higherpositioncol+1;i<=lowerpositioncol;i++){
            if (!imageState.get((higherpositionrow*numberOfColumns)+i)){
                connection1=false;
                break;
            }
            else if(i==lowerpositioncol) connection1=true;
        }
        for(int i=higherpositionrow;i<=lowerpositionrow-1;i++){
            if (!imageState.get((i*numberOfColumns)+lowerpositioncol)){
                connection2=false;
                break;
            }
            else if(i==lowerpositionrow-1) connection2=true;
        }
        if(connection1&&connection2){
            return true;
        }
        for(int i=higherpositionrow+1;i<=lowerpositionrow;i++){
            if (!imageState.get((i*numberOfColumns)+higherpositioncol)){
                connection1=false;
                break;
            }
            else if(i==lowerpositionrow) connection1=true;
        }
        for(int i=higherpositioncol;i<=lowerpositioncol-1;i++){
            if (!imageState.get((lowerpositionrow*numberOfColumns)+i)){
                connection2=false;
                break;
            }
            else if(i==lowerpositioncol-1) connection2=true;
        }
        if(connection1&&connection2){
            return true;
        }
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
