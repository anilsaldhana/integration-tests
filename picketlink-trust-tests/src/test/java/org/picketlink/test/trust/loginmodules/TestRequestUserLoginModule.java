/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors. 
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.picketlink.test.trust.loginmodules;

import javax.security.auth.login.LoginException;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;
import javax.servlet.http.HttpServletRequest;

import org.jboss.security.auth.spi.UsersRolesLoginModule;

/**
 * A test login module that just looks for a request param
 * "user"
 * @author Anil.Saldhana@redhat.com
 * @since Sep 13, 2011
 */
public class TestRequestUserLoginModule extends UsersRolesLoginModule
{
   @Override
   protected String[] getUsernameAndPassword() throws LoginException
   {
      //get the username from the request
      /** The JACC PolicyContext key for the current Subject */
      String WEB_REQUEST_KEY = "javax.servlet.http.HttpServletRequest";
      try
      {
         String password = null;
         HttpServletRequest request = (HttpServletRequest) PolicyContext.getContext(WEB_REQUEST_KEY);
         String username = request.getParameter("user");
         if(username != null && "UserA".equals(username))
         {
            password = "PassA";
         }
         else if(username != null && "UserB".equals(username))
         {
            password = "PassB";
         }
         return new String[] { username, password};
      }
      catch (PolicyContextException e)
      { 
         LoginException le = new LoginException();
         le.initCause(e);
         throw le;
      }
  }
}