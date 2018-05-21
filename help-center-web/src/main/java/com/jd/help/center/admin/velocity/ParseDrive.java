package com.jd.help.center.admin.velocity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Parse;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-9-1
 * Time: 17:54:53
 * To change this template use File | Settings | File Templates.
 */
public class ParseDrive extends Parse {
    Log log= LogFactory.getLog(ParseDrive.class);
    @Override
    public String getName() {
        return "parse1"; 
    }

    @Override
    public int getType() {
        return LINE;
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ParseErrorException, MethodInvocationException {
        try {
            return super.render(context, writer, node);
        } catch (ResourceNotFoundException e) {
            log.debug("Method:Catch Exception----->"+e.getMessage());
        }
        return false;
    }
}
