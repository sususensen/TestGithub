package FileTree;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.function.Function;

/**
 *
 * @author MK
 */

public class FileTreeItem extends TreeItem<String> {


    //判断树节点是否被初始化，没有初始化为真
    private boolean notInitialized = true;

    private final File file;
    private final Function<File, File[]> supplier;
  List<String> picturePath=new ArrayList<String>();

    public FileTreeItem(File file) {
        super(U.getFileName(file), U.getFileIconToNode(file));
        this.file = file;
        supplier = (File f) -> {
            if (((FileTreeItem) this.getParent()).getFile() == Main.ROOT_FILE) {
                String name = U.getFileName(f);
                if (name.equals("网络") || name.equals("家庭组")) {
                    return new File[0];
                }
            }
            //获取图片文件绝对路径；
            return f.listFiles();
        };
    }

    public FileTreeItem(File file, Function<File, File[]> supplier) {
        super(U.getFileName(file), U.getFileIconToNode(file));
        this.file = file;
        this.supplier = supplier;
    }
    class TwoReturn{

    }
    //重写getchildren方法，让节点被展开时加载子目录
    @Override
    public ObservableList<TreeItem<String>> getChildren() {

        ObservableList<TreeItem<String>> children = super.getChildren();
        //没有加载子目录时，则加载子目录作为树节点的孩子
        if (this.notInitialized && this.isExpanded()) {

            this.notInitialized = false;    //设置没有初始化为假

            /*判断树节点的文件是否是目录，
             *如果是目录，着把目录里面的所有的文件目录添加入树节点的孩子中。
             */
            for (File file1:this.file.listFiles()
                 ) {
                String path=file1.getAbsolutePath();
                if(path.contains("jpg")||path.contains("png")){
                    picturePath.add(path);
                    System.out.println(picturePath);
                   }
            }
            if (this.getFile().isDirectory()) {
                for (File f : supplier.apply(this.getFile())) {
                    FileTreeItem newFileTreeItem = new FileTreeItem(f);
                    children.add(newFileTreeItem);
                }

            }
        }
        return children;
    }
    //获取图片绝对路径；
    public List<String>getPicturePath(){
        return picturePath;
    }
    //重写叶子方法，如果该文件不是目录，则返回真
    @Override
    public boolean isLeaf() {

        return !file.isDirectory();
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }
}





