package ch.tbmelabs.tv.core.entryserver.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.tbmelabs.tv.shared.constants.security.SecurityRole;

@RestController
@PreAuthorize("hasAnyRole('" + SecurityRole.GANDALF + "')")
public class AdminController {
  @RequestMapping({ "/admin" })
  public String getAdmin() {
    return "you're admin";
  }
}