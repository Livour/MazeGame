public class Point {
    private int y;
    private int x;

    Point(int x, int y) { // | O(1)
        this.y = y;
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() { // | O(1)
        return x;
    }

    public int getY() { // | O(1)
        return y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "y=" + y +
                ", x=" + x +
                '}';
    }
}
