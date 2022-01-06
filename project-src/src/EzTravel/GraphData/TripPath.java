// This work is mine unless otherwise cited - Nathan Loria

package EzTravel.GraphData;

import java.util.*;

public class TripPath {
  private ArrayList<Integer> path = new ArrayList<Integer>();
  private double mtc = Double.POSITIVE_INFINITY;
  private final int N, init;
  private final double[][] distance;

  public TripPath(int init, double[][] distance) {
    N = distance.length;
    this.init = init;
    this.distance = distance;
  }

  public ArrayList<Integer> getPath() {
    return path;
  }

  private static boolean boolNI(int elem, int subset) {
    return ((1 << elem) & subset) == 0;
  }

  public static List<Integer> combo(int r, int n) {
    List<Integer> subsets = new ArrayList<>();
    combo(0, 0, r, n, subsets);
    return subsets;
  }

  private static void combo(int set, int at, int r, int n, List<Integer> subsets) {
    int elementsLeftToPick = n - at;
    if (elementsLeftToPick < r) {
      return;
    }
    if (r == 0) {
      subsets.add(set);
    } else {
      for (int i = at; i < n; i++) {
        set = set ^ (1 << i);
        combo(set, i + 1, r - 1, n, subsets);
        set = set ^ (1 << i);
      }
    }
  }

  public void solve() {
    final int fin = (1 << N) - 1;
    Double[][] memoize = new Double[N][1 << N];

    for (int end = 0; end < N; end++) {
      if (end == init) {
        continue;
      }
      memoize[end][(1 << init) | (1 << end)] = distance[init][end];
    }

    for (int r = 3; r <= N; r++) {
      for (int subset : combo(r, N)) {
        if (boolNI(init, subset)) {
          continue;
        }
        for (int next = 0; next < N; next++) {
          if (next == init || boolNI(next, subset)) {
            continue;
          }
          int subsetWithoutNext = subset ^ (1 << next);
          double minDist = Double.POSITIVE_INFINITY;
          for (int end = 0; end < N; end++) {
            if (end == init || end == next || boolNI(end, subset)) {
              continue;
            }
            double newDistance = memoize[end][subsetWithoutNext] + distance[end][next];
            if (newDistance < minDist) {
              minDist = newDistance;
            }
          }
          memoize[next][subset] = minDist;
        }
      }
    }

    for (int i = 0; i < N; i++) {
      if (i == init) {
        continue;
      }
      double tourCost = memoize[i][fin] + distance[i][init];
      if (tourCost < mtc) {
        mtc = tourCost;
      }
    }

    int lastIndex = init;
    int state = fin;
    path.add(init);

    for (int i = 1; i < N; i++) {
      int index = -1;
      for (int j = 0; j < N; j++) {
        if (j == init || boolNI(j, state)) {
          continue;
        }
        if (index == -1) {
          index = j;
        }
        double prevDist = memoize[index][state] + distance[index][lastIndex];
        double newDist = memoize[j][state] + distance[j][lastIndex];
        if (newDist < prevDist) {
          index = j;
        }
      }
      path.add(index);
      state = state ^ (1 << index);
      lastIndex = index;
    }
    path.add(init);
    Collections.reverse(path);
  }
}
