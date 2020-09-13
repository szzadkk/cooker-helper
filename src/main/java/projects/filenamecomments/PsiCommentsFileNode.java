package projects.filenamecomments;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.ui.SimpleTextAttributes;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * @author yanming
 */
public class PsiCommentsFileNode extends PsiFileNode {

  public PsiCommentsFileNode(Project project,
      @NotNull PsiFile value,
      ViewSettings viewSettings) {
    super(project, value, viewSettings);
  }

  @Override
  protected void updateImpl(@NotNull PresentationData data) {
    super.updateImpl(data);
  }

  @Override
  public void update(@NotNull PresentationData data) {
    VirtualFile virtualFile = this.getVirtualFile();
    if (virtualFile == null || !StringUtils.equals(virtualFile.getExtension(), "java")) {
      super.update(data);
      return;
    }
    String content = null;
    try {
      content = VfsUtil.loadText(virtualFile);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    int publicClassIndex = content.indexOf("public class");
    if (publicClassIndex == 0) {
      super.update(data);
      return;
    }
    int firstCommentIndex = content.indexOf("/**");
    if (firstCommentIndex == 0 || firstCommentIndex >= publicClassIndex) {
      super.update(data);
      return;
    }
    int commentEndIndex = content.indexOf('\n', firstCommentIndex);
    data.addText(this.myName, SimpleTextAttributes.GRAY_ATTRIBUTES);
    data.addText(content.substring(firstCommentIndex, commentEndIndex),
        SimpleTextAttributes.GRAYED_ITALIC_ATTRIBUTES);
    super.update(data);
  }
}
