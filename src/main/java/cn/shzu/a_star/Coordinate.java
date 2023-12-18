package cn.shzu.a_star;

import java.util.Objects;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description point coord
 * @date 2023/11/14 23:07:39
 */
public class Coordinate {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int [] getLocation(){
        return new int[]{x,y};
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
