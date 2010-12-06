/*
 * Copyright 2009-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.freedom.gj.beans.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is a wrapper for a log implementation.
 * Removing implementation details from our code base makes us less
 * visible to logging changes over time. Giving us an ability
 * to change the implementation only in one place. *
 * @author Martin Jamszolik
 */
public class Lg {
    
    
    public static final Level DEBUG = Level.DEBUG;
    public static final Level ERROR = Level.ERROR;
    public static final Level WARN = Level.WARN;
    public static final Level TRACE = Level.TRACE;
    public static final Level INFO = Level.INFO;



    /**
     * Use This utility method to print simple message without care to
     * indicate under what class this is happening.
     * @param level
     * @param msg
     */
    public static void log(Level level, Object msg ){
        log(null,level,msg,null);
    }


    /**
     * Use this Method to print to log with just a message, as no exception object is
     * available
     * @param logClass
     * @param level
     * @param msg
     */
    public static void log(Object logObject, Level level, Object msg ){
        log(logObject,level,msg,null);
    }



    /**
     * Use this method when you are trying to log a message along with Exception
     * @param logClass
     * @param level
     * @param msg
     * @param ex
     */
    public static void log(Object logObject, Level level,Object msg, Exception ex ){
        switch( level ){
            case ERROR:
                if( ex != null )
                    getLog(logObject.getClass()).error(msg,ex);
                else
                    getLog(logObject.getClass()).error(msg);
                break;
            case TRACE:
                if( ex != null )
                    getLog(logObject.getClass()).trace(msg,ex);
                else
                    getLog(logObject.getClass()).trace(msg);
                break;
            case DEBUG:
                if( ex != null )
                    getLog(logObject.getClass()).debug(msg,ex);
                else
                    getLog(logObject.getClass()).debug(msg);
                break;
            case WARN:
                 if( ex != null )
                    getLog(logObject.getClass()).warn(msg,ex);
                else
                    getLog(logObject.getClass()).warn(msg);
                 break;
            case INFO:
                 if( ex != null )
                    getLog(logObject.getClass()).info(msg,ex);
                else
                    getLog(logObject.getClass()).info(msg);
                 break;
            default:
                 if( ex != null )
                    getLog(logObject.getClass()).debug(msg,ex);
                else
                    getLog(logObject.getClass()).debug(msg);
        }
    }

 
  public static boolean isEnabled(Level level ){

      switch ( level ){
          case TRACE:
               return getLog(null).isTraceEnabled();
          case DEBUG:
               return getLog(null).isDebugEnabled();
          case INFO:
              return getLog(null).isInfoEnabled();
          case WARN:
              return getLog(null).isWarnEnabled();
          case ERROR:
              return getLog(null).isErrorEnabled();
      }

      return false;


  }


    private static Log getLog(Class<? extends Object> logClass) {
        Log log = null;
        try {
            log = LogFactory.getLog(logClass == null ? Lg.class : logClass);
        } finally {
            if (log == null) {
                log = LogFactory.getLog(Lg.class);
            }
        }
        return log;
    }

    public enum Level {
        DEBUG, ERROR, INFO, WARN,TRACE;
    }
}
