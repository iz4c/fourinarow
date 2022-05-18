public class TestData {

    // source: https://oeis.org/A212693
    // this is the number of unique positions that exist at a certain depth on a 7x6 grid
    final static long[] permutationsAtDepth = {
            1, 7, 49, 238, 1120, 4263, 16422, 54859, 184275, 558186, 1662623, 4568683, 12236101, 30929111, 75437595,
            176541259, 394591391, 858218743, 1763883894, 3568259802L, 6746155945L, 12673345045L, 22010823988L,
            38263228189L, 60830813459L, 97266114959L, 140728569039L, 205289508055L, 268057611944L, 352626845666L,
            410378505447L, 479206477733L, 488906447183L, 496636890702L, 433471730336L, 370947887723L, 266313901222L,
            183615682381L, 104004465349L, 55156010773L, 22695896495L, 7811825938L, 1459332899
    };

    public static long getPermutationsToDepth(int depth) {
        long total = 0;
        for (int i = 0; i < depth + 1; i++) {
            total += permutationsAtDepth[i];
        }

        return total;
    }

    // source: https://tromp.github.io/c4/c4.html
    final static long[][] permutationsForBoardSize = {
            {2, 3, 4, 5},
            {5, 18, 58, 179},
            {13, 116, 869, 6000},
            {35, 741, 12031, 161029},
            {96, 4688, 158911, 3945711}
    };

    public static long getPermutationsForBoardSize(int width, int height) {
        return permutationsForBoardSize[width - 1][height - 1];
    }

}
