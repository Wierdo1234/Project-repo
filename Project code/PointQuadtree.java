public class PointQuadtree {

    enum Quad {
        NW,
        NE,
        SW,
        SE
    }

    public PointQuadtreeNode root;

    public PointQuadtree() {
        this.root = null;
    }

    private boolean insertHelper(PointQuadtreeNode node, CellTower a) {
            if(a.x < node.celltower.x && a.y >= node.celltower.y ) {
                if(node.quadrants[0] == null) {
                    node.quadrants[0] = new PointQuadtreeNode(a);
                    return true;
                } else {
                    return insertHelper(node.quadrants[0], a);
                }
            }
            else if(a.x >= node.celltower.x && a.y > node.celltower.y) {
                if(node.quadrants[1] == null) {
                    node.quadrants[1] = new PointQuadtreeNode(a);
                    return true;
                } else {
                    return insertHelper(node.quadrants[1], a);
                }
            }
            else if(a.x <= node.celltower.x && a.y < node.celltower.y) {
                if(node.quadrants[2] == null) {
                    node.quadrants[2] = new PointQuadtreeNode(a);
                    return true;
                } else {
                    return insertHelper(node.quadrants[2], a);
                }
            }
            else {
                if(node.quadrants[3] == null) {
                    node.quadrants[3] = new PointQuadtreeNode(a);
                    return true;
                } else {
                    return insertHelper(node.quadrants[3], a);
                }
            }
    }

    public boolean insert(CellTower a) {
        if(root == null) {
            root = new PointQuadtreeNode(a);
            return true;
        }
        if(cellTowerAt(a.x, a.y)) return false;
        return insertHelper(root, a);
    }

    private boolean cellTowerAtHelper(PointQuadtreeNode node, int x, int y) {
        if(node == null) {
            return false;
        }
        else if(x == node.celltower.x && y == node.celltower.y) {
            return true;
        }
        else if(x < node.celltower.x && y >= node.celltower.y) {
            return cellTowerAtHelper(node.quadrants[0], x, y);
        }
        else if(x >= node.celltower.x && y > node.celltower.y) {
            return cellTowerAtHelper(node.quadrants[1], x, y);
        }
        else if(x <= node.celltower.x && y < node.celltower.y) {
            return cellTowerAtHelper(node.quadrants[2], x, y);
        }
        else {
            return cellTowerAtHelper(node.quadrants[3], x, y);
        }
    }

    public boolean cellTowerAt(int x, int y) {
        return cellTowerAtHelper(root, x, y);
    }

    private CellTower compareCheapest(CellTower cheapest, CellTower C1, CellTower C2, CellTower C3, CellTower C4) {
        if(cheapest != null) {
            if(C1 != null && cheapest.cost > C1.cost) cheapest = C1;
            if(C2 != null && cheapest.cost > C2.cost) cheapest = C2;
            if(C3 != null && cheapest.cost > C3.cost) cheapest = C3;
            if(C4 != null && cheapest.cost > C4.cost) cheapest = C4;
        }
        else {
            if(C1 != null) {
                cheapest = C1;
                if(C2 != null && cheapest.cost > C2.cost) cheapest = C2;
                if(C3 != null && cheapest.cost > C3.cost) cheapest = C3;
                if(C4 != null && cheapest.cost > C4.cost) cheapest = C4;
            }
            if(C2 != null) {
                cheapest = C2;
                if(C3 != null && cheapest.cost > C3.cost) cheapest = C3;
                if(C4 != null && cheapest.cost > C4.cost) cheapest = C4;
            }
            if(C3 != null) {
                cheapest = C3;
                if(C4 != null && cheapest.cost > C4.cost) cheapest = C4;
            }
            if(C4 != null) {
                cheapest = C4;
            }
        }
        return cheapest;
    }
    private CellTower chooseCellTowerHelper(PointQuadtreeNode node, int x, int y, int r) {
        if(node == null) return null;
        else {
            CellTower cheap1 = chooseCellTowerHelper(node.quadrants[0], x, y, r);
            CellTower cheap2 = chooseCellTowerHelper(node.quadrants[1], x, y, r);
            CellTower cheap3 = chooseCellTowerHelper(node.quadrants[2], x, y, r);
            CellTower cheap4 = chooseCellTowerHelper(node.quadrants[3], x, y, r);
            CellTower cheapest = null;
            if(node.celltower.distance(x, y) <= r) cheapest = node.celltower;
            return compareCheapest(cheapest, cheap1, cheap2, cheap3, cheap4);
        }
    }
    public CellTower chooseCellTower(int x, int y, int r) {
        return chooseCellTowerHelper(root, x, y, r);
    }
    

}
