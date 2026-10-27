import java.util.*;

public class MinimumCostInfrastructurePlanner {

    static class Edge {
        int u, v, w;

        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    static class DSU {
        int[] parent, rank;

        DSU(int n) {
            parent = new int[n];
            rank = new int[n];

            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        boolean union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);

            if (rootA == rootB) {
                return false;
            }

            if (rank[rootA] < rank[rootB]) {
                parent[rootA] = rootB;
            } else if (rank[rootA] > rank[rootB]) {
                parent[rootB] = rootA;
            } else {
                parent[rootB] = rootA;
                rank[rootA]++;
            }

            return true;
        }
    }

    static void kruskal(int n, List<Edge> edges) {
        Collections.sort(edges, Comparator.comparingInt(e -> e.w));

        DSU dsu = new DSU(n);
        int totalCost = 0;
        int count = 0;

        System.out.println("\nKruskal's Algorithm:");

        for (Edge e : edges) {
            if (dsu.union(e.u, e.v)) {
                System.out.println(e.u + " - " + e.v
                        + " : " + e.w);

                totalCost += e.w;
                count++;

                if (count == n - 1) {
                    break;
                }
            }
        }

        if (count == n - 1) {
            System.out.println("Minimum Total Cost: " + totalCost);
        } else {
            System.out.println("Graph is disconnected.");
        }
    }

    static void prim(int n, List<Edge> edges) {
        List<List<Edge>> adj = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        for (Edge e : edges) {
            adj.get(e.u).add(new Edge(e.u, e.v, e.w));
            adj.get(e.v).add(new Edge(e.v, e.u, e.w));
        }

        PriorityQueue<Edge> pq = new PriorityQueue<>(
                Comparator.comparingInt(e -> e.w));

        boolean[] visited = new boolean[n];

        pq.add(new Edge(-1, 0, 0));

        int totalCost = 0;
        int count = 0;

        System.out.println("\nPrim's Algorithm:");

        while (!pq.isEmpty() && count < n) {
            Edge e = pq.poll();
            int u = e.v;

            if (visited[u]) {
                continue;
            }

            visited[u] = true;
            totalCost += e.w;

            if (e.u != -1) {
                System.out.println(e.u + " - " + u
                        + " : " + e.w);
            }

            count++;

            for (Edge next : adj.get(u)) {
                if (!visited[next.v]) {
                    pq.add(next);
                }
            }
        }

        if (count == n) {
            System.out.println("Minimum Total Cost: " + totalCost);
        } else {
            System.out.println("Graph is disconnected.");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of locations and connections: ");
        int n = sc.nextInt();
        int m = sc.nextInt();

        List<Edge> edges = new ArrayList<>();

        System.out.println("Enter each connection (u v cost):");

        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();

            edges.add(new Edge(u, v, w));
        }

        kruskal(n, new ArrayList<>(edges));
        prim(n, edges);

        sc.close();
    }
}
