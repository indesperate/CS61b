import edu.princeton.cs.introcs.StdAudio;
import synthesizer.GuitarString;

import java.util.ArrayList;
import java.util.List;

/** full keyboard to play sound. */
public class GuitarHero {
    private static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private static final List<GuitarString> guitarStringArray = new ArrayList<>(keyboard.length());


    public static void main(String[] args) {
        for (int i = 0; i < keyboard.length(); i += 1) {
            double frequency = 440.0 * Math.pow(2.0, (i - 24) / 12.0);
            guitarStringArray.add(new GuitarString(frequency));
        }
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = keyboard.indexOf(key);
                guitarStringArray.get(index).pluck();
            }
            double sample = 0.0;
            for (int i = 0; i < keyboard.length(); i += 1) {
                GuitarString gs = guitarStringArray.get(i);
                sample += gs.sample();
                gs.tic();
            }
            StdAudio.play(sample);
        }
    }
}
