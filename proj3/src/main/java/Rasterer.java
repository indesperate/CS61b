import java.beans.BeanInfo;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        //System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double lrlat = params.get("lrlat");
        double ullat = params.get("ullat");
        double w = params.get("w");
        double h = params.get("h");
        double LonDPPQuery = (lrlon - ullon) / w;
        double LonDPPOrigin = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE;
        int depth = (int)Math.ceil(Math.log(LonDPPOrigin / LonDPPQuery) / Math.log(2));
        if (depth > 7) {
            depth = 7;
        }
        double latRange = MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT;
        double lonRange = MapServer.ROOT_LRLON - MapServer.ROOT_ULLON;
        double latPhase = latRange / Math.pow(2, depth);
        double lonPhase = lonRange / Math.pow(2, depth);
        int BeginLatNum = (int)Math.floor((MapServer.ROOT_ULLAT - ullat) / latPhase);
        int EndLatNum = (int)Math.floor((MapServer.ROOT_ULLAT - lrlat) / latPhase);
        int BeginLonNum = (int)Math.floor((ullon - MapServer.ROOT_ULLON) / lonPhase);
        int EndLonNum = (int)Math.floor((lrlon - MapServer.ROOT_ULLON) / lonPhase);
        if (BeginLonNum < 0) {
            BeginLonNum = 0;
        }
        if (BeginLatNum < 0) {
            BeginLatNum = 0;
        }
        if (EndLonNum >= Math.pow(2,depth)) {
            EndLonNum = (int)Math.pow(2, depth) - 1;
        }
        if (EndLatNum >= Math.pow(2,depth)) {
            EndLatNum = (int)Math.pow(2, depth) - 1;
        }
        int LonNumRange = EndLonNum - BeginLonNum + 1;
        int LatNumRange = EndLatNum - BeginLatNum + 1;
        String[][] renderGrid = new String[LatNumRange][LonNumRange];
        for (int i = 0; i < LatNumRange; i += 1) {
            for (int j = 0; j < LonNumRange; j += 1) {
                renderGrid[i][j] = "d" + depth + "_x" + (j + BeginLonNum) + "_y" + (i + BeginLatNum) + ".png";
            }
        }
        //System.out.println(Arrays.deepToString(renderGrid));
        results.put("render_grid", renderGrid);
        results.put("depth", depth);
        results.put("raster_ul_lat", MapServer.ROOT_ULLAT - BeginLatNum * latPhase);
        results.put("raster_lr_lat", MapServer.ROOT_ULLAT - (EndLatNum + 1) * latPhase);
        results.put("raster_ul_lon", MapServer.ROOT_ULLON + BeginLonNum * lonPhase);
        results.put("raster_lr_lon", MapServer.ROOT_ULLON + (EndLonNum + 1) * lonPhase);
        results.put("query_success", true);
        //System.out.println("DONE");
        return results;
    }

}
