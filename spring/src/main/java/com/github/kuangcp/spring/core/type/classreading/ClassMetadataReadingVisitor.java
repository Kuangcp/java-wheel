package com.github.kuangcp.spring.core.type.classreading;

import com.github.kuangcp.spring.core.type.ClassMetadata;
import lombok.Getter;
import lombok.Setter;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.Opcodes;
import org.springframework.asm.SpringAsmInfo;
import org.springframework.util.ClassUtils;

/**
 * @author https://github.com/kuangcp on 2019-12-14 17:36
 */
@Setter
@Getter
public class ClassMetadataReadingVisitor extends ClassVisitor implements ClassMetadata {

  private String className;

  private boolean isInterface;

  private boolean isAbstract;

  private boolean isFinal;

  private String superClassName;

  private String[] interfaces;

  public ClassMetadataReadingVisitor() {
    super(SpringAsmInfo.ASM_VERSION);
  }

  public void visit(int version, int access, String name, String signature, String supername,
      String[] interfaces) {
    this.className = ClassUtils.convertResourcePathToClassName(name);
    this.isInterface = ((access & Opcodes.ACC_INTERFACE) != 0);
    this.isAbstract = ((access & Opcodes.ACC_ABSTRACT) != 0);
    this.isFinal = ((access & Opcodes.ACC_FINAL) != 0);
    if (supername != null) {
      this.superClassName = ClassUtils.convertResourcePathToClassName(supername);
    }
    this.interfaces = new String[interfaces.length];
    for (int i = 0; i < interfaces.length; i++) {
      this.interfaces[i] = ClassUtils.convertResourcePathToClassName(interfaces[i]);
    }
  }

  public boolean isConcrete() {
    return !(this.isInterface || this.isAbstract);
  }

  public boolean hasSuperClass() {
    return (this.superClassName != null);
  }

  public String[] getInterfaceNames() {
    return this.interfaces;
  }
}
