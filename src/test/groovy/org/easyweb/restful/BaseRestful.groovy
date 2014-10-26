package org.easyweb.restful

import org.junit.After
import org.junit.Before
import org.easyweb.server.JettyServer

import java.util.concurrent.CountDownLatch

/**
*  this is a org.easyweb.restful base test
*
**/
class BaseRestful {

	def server 

	def latch = new CountDownLatch(1)
	@Before
	public void setUp() {
		server = new JettyServer()
		server.start(latch)

        print "setup Ok\n"
	}

	@After
	public void tearDown() {
		server.stop()

        print "stop ok\n"
	}
}
