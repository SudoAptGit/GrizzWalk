package com.grizzzwalk.mjschmidt.grizzwalk;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by austindanaj on 10/13/16.
 */

public class Mapping {

    private static final int DIAGONAL_COST = 14;
    private static final int VERTICAL_COST = 10;
    private static final int HORIZONTAL_COST = 10;

    /**
     *
     */
    private static class Cell {
        int heuristicCost = 0;
        int finalCost = 0;
        int x, y;
        Cell parent;
        Cell(int x, int y){
            this.x = x;
            this.y = y;
        }
        @Override
        public String toString(){
            return "["+this.x+", "+this.y+"]";
        }
    }

    private static Cell[][] grid = new Cell[8][8];
    private static PriorityQueue<Cell> open;
    private static boolean closed[][];
    private static int startX, startY;
    private static int endX, endY;

    /**
     *
     * @param x
     * @param y
     */
    private static void setBlocked(int x, int y){
        grid[x][y] = null;
    }

    /**
     *
     * @param x
     * @param y
     */
    private static void setStartCell(int x, int y){
        startX = x;
        startY = y;
    }

    /**
     *
     * @param x
     * @param y
     */
    private static void setEndCell(int x, int y){
        endX = x;
        endY = y;
    }

    /**
     *
     * @param current
     * @param adjacent
     * @param cost
     */
    private static void checkAndUpdateCost(Cell current, Cell adjacent, int cost){
        if(adjacent == null || closed[adjacent.x][adjacent.y]){
            return;
        }
        int adjFinalCost = adjacent.heuristicCost+cost;
        boolean inOpen = open.contains(adjacent);
        if(!inOpen || adjFinalCost < adjacent.finalCost){
            adjacent.finalCost = adjFinalCost;
            adjacent.parent = current;
            if(!inOpen){
                open.add(adjacent);
            }
        }
    }
    /**
     *
     */
    private static void AStar(){
        open.add(grid[startX][startY]);

        Cell current;

        while (true){
            current = open.poll();
            if(current == null){
                break;
            }
            closed[current.x][current.y] = true;

            if (current.equals(grid[endX][endY])){
                return;
            }

            Cell adjacent;
            if(current.x - 1 >= 0){
                adjacent = grid[current.x - 1][current.y];
                checkAndUpdateCost(current, adjacent, current.finalCost + HORIZONTAL_COST);
                if (current.y - 1 >= 0){
                    adjacent = grid[current.x-1][current.y - 1];
                    checkAndUpdateCost(current, adjacent, current.finalCost + DIAGONAL_COST);
                }
                if(current.y + 1 < grid[0].length){
                    adjacent = grid[current.x - 1][current.y + 1];
                    checkAndUpdateCost(current, adjacent, current.finalCost + DIAGONAL_COST);
                }
            }
            if(current.y - 1 >= 0){
                adjacent = grid[current.x][current.y - 1];
                checkAndUpdateCost(current, adjacent, current.finalCost + VERTICAL_COST);
            }
            if(current.y + 1 < grid[0].length){
                adjacent = grid[current.x][current.y + 1];
                checkAndUpdateCost(current, adjacent, current.finalCost + VERTICAL_COST);
            }
            if(current.x + 1 < grid.length){
                adjacent = grid[current.x + 1][current.y];
                checkAndUpdateCost(current, adjacent, current.finalCost + HORIZONTAL_COST);
                if(current.y - 1 >= 0){
                    adjacent = grid[current.x + 1][current.y + 1];
                    checkAndUpdateCost(current, adjacent, current.finalCost + DIAGONAL_COST);
                }
                if(current.y + 1 < grid[0].length){
                    adjacent = grid[current.x + 1][current.y + 1];
                    checkAndUpdateCost(current, adjacent, current.finalCost + DIAGONAL_COST);
                }
            }
        }
    }

    /**
     *
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param blocked
     */
    public void run(int startX, int startY, int endX, int endY, int[][] blocked){
        grid = new Cell[9][9];
        closed = new boolean[9][9];
        open = new PriorityQueue<>(15, new Comparator<Cell>() {
            @Override
            public int compare(Cell c1, Cell c2) {
                return c1.finalCost < c2.finalCost? -1 : c1.finalCost > c2.finalCost? 1 : 0;
            }
        });

        setStartCell(startX, startY);
        setEndCell(endX, endY);
        for(int i = 0; i < 9; ++i){
            for(int j = 0; j < 9; ++j){
                grid[i][j] = new Cell(i,j);
                grid[i][j].heuristicCost = Math.abs(i-endX) + Math.abs(j-endY);
            }
        }
        grid[startX][startY].finalCost = 0;
        for (int i = 0; i < blocked.length; ++i){
            setBlocked(blocked[i][0], blocked[i][1]);
        }
        /**
         *
         */
        System.out.println("Grid: ");
        for(int i=0;i<9;++i){
            for(int j=0;j<9;++j){
                if(i==startX&&j==startY)System.out.print("SO  "); //Source
                else if(i==endX && j==endY)System.out.print("DE  "); //Destination
                else if(grid[i][j]!= null) System.out.printf("%-3d ", 0);
                else System.out.print("BL  ");
            }
            System.out.println();
        }
        System.out.println();


        AStar();

        /**
         *
         */
        System.out.println("\nScores for cells: ");
        for(int i=0;i<9;++i){
            for(int j=0;j<9;++j){
                if(grid[i][j]!= null) {
                    System.out.printf("%-3d ", grid[i][j].finalCost);
                }else {
                    System.out.print("BL  ");
                }
            }
            System.out.println();
        }
        System.out.println();
        if(closed[endX][endY]){
            System.out.println("Path: ");
            Cell current = grid[endX][endY];
            System.out.print(grid[endX][endY]);
            String direction = grid[endX][endY].toString();
            while(current.parent != null){
                System.out.print(" -> " + current.parent);
                direction = direction + "," + current.parent.toString();
                current = current.parent;
            }
            setIndices (direction);
            System.out.println();
        }else{
            System.out.println("No Possible path");
        }
    }
    private String indices = "";

    public void setIndices(String indices){
        this.indices = indices;
    }
    public String getIndices(){
        return this.indices;
    }

}

