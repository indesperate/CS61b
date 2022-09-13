package Discussion6;

import edu.princeton.cs.algs4.In;

public class AltList<X, Y> {
    private X item;
    private AltList<Y, X> next;

    AltList(X item, AltList<Y, X> next) {
        this.item = item;
        this.next = next;
    }

    public AltList<Y, X> pairsSwapped() {
        AltList<Y, X> result = new AltList<>(next.item,
                    new AltList<>(item, null)
                );
        if (next.next != null) {
            result.next.next = this.next.next.pairsSwapped();
        }
        return result;
    }

    public static void main(String[] args) {
        AltList<Integer, String> list =
                new AltList<Integer, String>(5,
                        new AltList<String, Integer>("cat",
                                new AltList<Integer, String>(10,
                                        new AltList<String, Integer>("dog", null)
                                )
                        )
                );
    }
}
