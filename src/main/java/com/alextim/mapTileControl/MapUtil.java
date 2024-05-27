package com.alextim.mapTileControl;

import java.util.List;

public class MapUtil {

    public static final int TILE_SIZE = 256;

    /*Перевод  геодезических (широта, долгота) координат в декартовы (картографические)*/
    public static CartographicCoord latLonToCartographicCoord(LatLonCoord latLonCoord, int zoom) {
        double bm0 = TILE_SIZE * Math.pow(2, zoom) / 2;

        double latRad = latLonCoord.latitude * Math.PI / 180.0;
        double lonRad = latLonCoord.longitude * Math.PI / 180.0;

        double temp = Math.log((1 + Math.sin(latRad)) / (1 - Math.sin(latRad)));

        int x = (int) Math.round(bm0 * (1 + lonRad / Math.PI));
        int y = (int) Math.round(bm0 * (1 - 0.5 * temp / Math.PI));
        return new CartographicCoord(x, y);
    }

    /*Перевод декартовых координат в георграфических*/
    public static LatLonCoord cartographicToLatLonCoord(CartographicCoord cartographicCoord, int zoom) {
        double bm0 = TILE_SIZE * Math.pow(2, zoom) / 2;

        double lonRad = Math.PI * (cartographicCoord.x - bm0) / bm0;

        double temp = Math.exp(2 * Math.PI * (bm0 - cartographicCoord.y) / bm0);
        double latRad = Math.asin((temp - 1) / (temp + 1));

        double latitude = 180 * latRad / Math.PI;
        double longitude = 180 * lonRad / Math.PI;
        return new LatLonCoord(latitude, longitude);
    }

    /*Перевод декартовых координат в координаты тайла*/
    public static TileCoord cartographicToTileCoord(CartographicCoord cartographicCoord) {
        int x = cartographicCoord.x / TILE_SIZE;
        int y = cartographicCoord.y / TILE_SIZE;
        int dX = cartographicCoord.x % TILE_SIZE;
        int dY = cartographicCoord.y % TILE_SIZE;
        return new TileCoord(x, y, dX, dY);
    }

    /*Перевод координат тайла в декартовые координаты*/
    public static CartographicCoord tileToCartographicCoord(TileCoord tileXY) {
        int x = tileXY.x * TILE_SIZE + tileXY.dX;
        int y = tileXY.y * TILE_SIZE + tileXY.dY;
        return new CartographicCoord(x, y);
    }

    public static class LatLonCoord {
        public double latitude, longitude;

        public LatLonCoord(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public LatLonCoord() {
        }

        @Override
        public String toString() {
            return "LatLonCoord{" + "latitude=" + latitude + ", longitude=" + longitude + '}';
        }
    }

    public static class TileCoord {
        public int x, y;     //номер тайла
        public int dX, dY;  //координты в тайле

        public TileCoord(int x, int y, int dX, int dY) {
            this.x = x;
            this.y = y;
            this.dX = dX;
            this.dY = dY;
        }

        @Override
        public String toString() {
            return "TileCoord{" + "x=" + x + ", y=" + y + ", dX=" + dX + ", dY=" + dY + '}';
        }
    }

    public static class CartographicCoord {
        public int x, y;

        public CartographicCoord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "CartographicCoord{" + "x=" + x + ", y=" + y + '}';
        }
    }

    public static boolean doPointsBelongToPolygon(List<LatLonCoord> points, double coordX, double coordY) {
        int cross = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            if (points.get(i).longitude == points.get(i + 1).longitude &&
                    points.get(i).latitude == points.get(i + 1).latitude)
                continue;

            int res = doesRayFromPointLineCrossLines(
                    points.get(i).longitude,
                    points.get(i).latitude,
                    points.get(i + 1).longitude,
                    points.get(i + 1).latitude,
                    coordX,
                    coordY);

            if (res == 1)
                cross++;

            if (res == -1)
                return false;
        }

        return cross % 2 != 0;
    }

    /*Пересекает ли луч из точки линию*/
    public static int doesRayFromPointLineCrossLines(double x1, double y1, double x2, double y2, double x, double y) {
        //Проверка пересечения луча первой вершины ребра
        if (doesPointBelongToLine(x, y, Integer.MAX_VALUE, y, x1, y1)) {
            //"Пересекаем вершину";
            return -1;
        }

        //Проверка на палаллеьность луча и ребра
        double v = multiVector(Integer.MAX_VALUE - x, y, x2 - x1, y2 - y1);
        if (v == 0) {
            //"Паралельны";
            return -1;
        }
        //Проверка на пересечение
        double v1 = multiVector(Integer.MAX_VALUE - x, y - y, x1 - x, y1 - y);
        double v2 = multiVector(Integer.MAX_VALUE - x, y - y, x2 - x, y2 - y);
        double v3 = multiVector(x2 - x1, y2 - y1, x - x1, y - y1);
        double v4 = multiVector(x2 - x1, y2 - y1, Integer.MAX_VALUE - x1, y - y1);

        if (v1 * v2 < 0 && v3 * v4 < 0)
            return 1; //Пересекает
        else
            return 0; //Не пересекат
    }

    /*Принадлежит точка к линии*/
    public static boolean doesPointBelongToLine(double x1, double y1, double x2, double y2, double x, double y) {
        double v1 = multiVector(x2 - x1, y2 - y1, x - x1, y - y1);
        boolean v2 = ((x1 < x && x < x2) || (x2 < x && x < x1));
        return v1 == 0 && v2;
    }

    /*Векторное произведение двух векторов*/
    public static double multiVector(double ax, double ay, double bx, double by) {
        return ax * by - bx * ay;
    }
}
