import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    double latPhase;
    double lonPhase;
    int beginLatNum;
    int endLatNum;
    int beginLonNum;
    int endLonNum;
    int depth;

    public Rasterer() {
    }

    private void calculateDepth(double lrlon, double ullon, double w) {
        double lonDPPQuery = (lrlon - ullon) / w;
        double lonDPPOrigin = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE;
        depth = (int) Math.ceil(Math.log(lonDPPOrigin / lonDPPQuery) / Math.log(2));
        if (depth > 7) {
            depth = 7;
        }
    }

    private void calculateSteps(double ullat, double lrlat, double ullon, double lrlon) {
        double latRange = MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT;
        double lonRange = MapServer.ROOT_LRLON - MapServer.ROOT_ULLON;
        latPhase = latRange / Math.pow(2, depth);
        lonPhase = lonRange / Math.pow(2, depth);
        beginLatNum = (int) Math.floor((MapServer.ROOT_ULLAT - ullat) / latPhase);
        endLatNum = (int) Math.floor((MapServer.ROOT_ULLAT - lrlat) / latPhase);
        beginLonNum = (int) Math.floor((ullon - MapServer.ROOT_ULLON) / lonPhase);
        endLonNum = (int) Math.floor((lrlon - MapServer.ROOT_ULLON) / lonPhase);
    }

    private void checkSteps() {
        if (beginLonNum < 0) {
            beginLonNum = 0;
        }
        if (beginLatNum < 0) {
            beginLatNum = 0;
        }
        if (endLonNum >= Math.pow(2, depth)) {
            endLonNum = (int) Math.pow(2, depth) - 1;
        }
        if (endLatNum >= Math.pow(2, depth)) {
            endLatNum = (int) Math.pow(2, depth) - 1;
        }
    }

    private String[][] createRenderGrid() {
        int lonNumRange = endLonNum - beginLonNum + 1;
        int latNumRange = endLatNum - beginLatNum + 1;
        String[][] renderGrid = new String[latNumRange][lonNumRange];
        for (int i = 0; i < latNumRange; i += 1) {
            for (int j = 0; j < lonNumRange; j += 1) {
                renderGrid[i][j] = "d" + depth + "_x" + (j + beginLonNum) + "_y" + (i + beginLatNum) + ".png";
            }
        }
        return renderGrid;
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     * The grid of images must obey the following properties, where image in the
     * grid is referred to as a "tile".
     * <ul>
     *     <li>The tiles collected must cover the most longitudinal distance per pixel
     *     (LonDPP) possible, while still covering less than or equal to the amount of
     *     longitudinal distance per pixel in the query box for the user viewport size. </li>
     *     <li>Contains all tiles that intersect the query bounding box that fulfill the
     *     above condition.</li>
     *     <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     * </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     * forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        Map<String, Object> results = new HashMap<>();
        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double lrlat = params.get("lrlat");
        double ullat = params.get("ullat");
        double w = params.get("w");
        calculateDepth(lrlon, ullon, w);
        calculateSteps(ullat, lrlat, ullon, lrlon);
        checkSteps();
        String[][] renderGrid = createRenderGrid();
        results.put("render_grid", renderGrid);
        results.put("depth", depth);
        results.put("raster_ul_lat", MapServer.ROOT_ULLAT - beginLatNum * latPhase);
        results.put("raster_lr_lat", MapServer.ROOT_ULLAT - (endLatNum + 1) * latPhase);
        results.put("raster_ul_lon", MapServer.ROOT_ULLON + beginLonNum * lonPhase);
        results.put("raster_lr_lon", MapServer.ROOT_ULLON + (endLonNum + 1) * lonPhase);
        results.put("query_success", true);
        return results;
    }

}
