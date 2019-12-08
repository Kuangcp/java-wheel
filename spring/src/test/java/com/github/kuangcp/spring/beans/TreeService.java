package com.github.kuangcp.spring.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author https://github.com/kuangcp on 2019-12-01 22:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeService {

  private TreeDao treeDao;
  private String version;
}
