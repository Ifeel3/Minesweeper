package minesweeper;

public class Cell {
    private final Type type;
    private boolean opened = false;
    private int num = 1;
    private boolean flag = false;

    Cell(Type typeOfCell) {
        type = typeOfCell;
    }

    public void addNum() {
        num++;
    }

    public void setOpened() {
        this.opened = true;
    }

    public boolean isOpened() {
        return opened;
    }

    public Type getType() {
        return type;
    }

    public boolean getFlag(){
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        if (type == Type.EMPTY && opened) {
            return "/";
        } else if (type == Type.NUMBER && opened) {
            return Integer.toString(num);
        } else if (type == Type.MINE && opened) {
            return "X";
        }
        if (flag) {
            return "*";
        }
        return ".";
    }
}
