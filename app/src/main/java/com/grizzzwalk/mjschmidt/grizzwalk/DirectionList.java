package com.grizzzwalk.mjschmidt.grizzwalk;

/**
 * Created by austindanaj on 10/17/16.
 */

public class DirectionList {

    private int pond = 0;
    private String indices = "";
    private String direction = "";
    private Building building = Building.getInstance();
    String [] list;
    public DirectionList(String indices){
        setIndices(indices);
    }
    private void setIndices(String newIndices){
        this.indices = newIndices;
    }
    private int[][] getCoordinates() {
        indices = indices.replace("[","");
        indices = indices.replace("]", "");
        indices = indices.replaceAll("\\s", "");
        String[] list = indices.split(",");
        String[] reverse = new String[list.length];
        int x = -1;
        int y = -1;
        int index = 0;
        int [][] coordinate = new int[list.length / 2][2];
        for( int i = 0; i < list.length; i++){
            reverse[i] = list[list.length - 1 - i];
            if( i % 2 == 0){
                y = Integer.valueOf(reverse[i]).intValue();
                coordinate[index][0] = y;
            }else{
                x = Integer.valueOf(reverse[i]).intValue();
                coordinate[index][1] = x;
                index++;
            }


        }
        if(index < list.length){

        }
        return coordinate;
    }
    private int incrementIndex = 0;
    public String[] getDirectionList(){

        pond = 0;
        int[][] coord = getCoordinates();
        list = new String[coord.length];
        int index = 0;
        int temp = find(0, building.getList().length, building.getList(), coord[index+1][0], coord[index+1][1]);
        if( coord[0][0] > coord[1][0]){
            if( coord[0][1] > coord[1][1]){
                direction = "NorthWest";
                list[index] = "Head " + direction + " towards " + building.getNames()[temp];
            }else if( coord[0][1] < coord[1][1]){
                direction = "SouthWest";
                list[index] = "Head " + direction + " towards " + building.getNames()[temp];
            }else{
                direction = "West";
                list[index] = "Head " + direction + " towards " + building.getNames()[temp];
            }
        }else if(coord[0][0] < coord[1][0]){
            if( coord[0][1] > coord[1][1]){
                direction = "NorthEast";
                list[index] = "Head " + direction + " towards " + building.getNames()[temp];
            }else if( coord[0][1] < coord[1][1]){
                direction = "SouthEast";
                list[index] = "Head " + direction + " towards " + building.getNames()[temp];
            }else{
                direction = "East";
                list[index] = "Head " + direction + " towards " + building.getNames()[temp];
            }
        }else{
            if( coord[0][1] > coord[1][1]){
                direction = "North";
                list[index] = "Head " + direction + " towards " + building.getNames()[temp];
            }else if( coord[0][1] < coord[1][1]){
                direction = "South";
                list[index] = "Head " + direction + " towards " + building.getNames()[temp];
            }
        }
        if(list[index].contains("Pond") && pond == 0){
            list[index] = "Head " + direction + " towards The First Pond";
        }else if(list[index].contains("Pond") && pond == 1){
            list[index] = "Head " + direction + " towards The Second Pond";
        }
        if (list[index].contains("Walking")){
            list[index] = "Head walking " + direction;
        }
        index++;
        for( int i = 1; i < coord.length-1; i++, index++){

            temp = find(0, building.getList().length, building.getList(), coord[i+1][0], coord[i+1][1]);
            directional(coord, i, index + incrementIndex, temp);

        }

        return list;
    }
    private void directional(int [][] coord, int i, int index, int listIndex){

        if( coord[i][0] > coord[i+1][0]){
            if( coord[i][1] > coord[i+1][1]){
                direction = "NorthWest";
                list[index] = "Continue " + direction + " towards " + building.getNames()[listIndex];
            }else if( coord[i][1] < coord[i+1][1]){
                direction = "SouthWest";
                list[index] = "Continue " + direction + " towards " + building.getNames()[listIndex];
            }else{
                direction = "West";
                list[index] = "Continue " + direction + " towards " + building.getNames()[listIndex];
            }
        }else if(coord[i][0] < coord[i+1][0]){
            if( coord[i][1] > coord[i+1][1]){
                direction = "NorthEast";
                list[index] = "Continue " + direction + " towards " + building.getNames()[listIndex];
            }else if( coord[i][1] < coord[i+1][1]){
                direction = "SouthEast";
                list[index] = "Continue " + direction + " towards " + building.getNames()[listIndex];
            }else{
                direction = "East";
                list[index] = "Continue " + direction + " towards " + building.getNames()[listIndex];
            }
        }else{
            if( coord[i][1] > coord[i+1][1]){
                direction = "North";
                list[index] = "Continue " + direction + " towards " + building.getNames()[listIndex];
            }else if( coord[i][1] < coord[i+1][1]){
                direction = "South";
                list[index] = "Continue " + direction + " towards " + building.getNames()[listIndex];
            }
        }
        if(list[index].contains("Pond") && pond == 0){
            list[index] = "Continue " + direction + " towards The First Pond";
            pond++;
        }else if(list[index].contains("Pond") && pond == 1){
            list[index] = "Continue " + direction + " towards The Second Pond";
            pond++;
        }
        if (list[index].contains("Walking")){
            list[index] = "Continue walking " + direction;
        }
    }
    private int find(int min, int max, int [][] list, int x, int y){

        if (max < min) {
            return -1; //not found
        } //end if
        int middle = min + ((max - min)/2); // Divide and conquer

        if (list[middle][0] > y){
            return find(min, middle-1, list, x, y);
        } else if (list[middle][0] < y){
            return find(middle+1, max, list, x, y);
        } else{
            if (list[middle][1] > x){
                return find(min, middle-1, list, x, y);
            }else if(list[middle][1] < x){
                return find(middle+1, max, list, x, y);
            }else{
                return middle;
            } // end nested if
        } //end if
    } // end find function
}
