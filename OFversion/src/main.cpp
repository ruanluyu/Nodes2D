//#include "ofMain.h"
//#include "ofApp.h"
#include "NBase.h"
#include "NSystem.h"
#include "NNodeObject.h"
#include <iostream>
using namespace std;
#define PAUSE cin >> _in
//ofApp* ofParent;

//========================================================================
int main( ){
	int _in;
	//-----------------------------------Test



	//-----------------------------------
	PAUSE;
	/*ofSetupOpenGL(1024,768,OF_WINDOW);			// <-------- setup the GL context
	ofApp *ske = new ofApp();
	ofParent = ske;*/
	// this kicks off the running of my app
	// can be OF_WINDOW or OF_FULLSCREEN
	// pass in width and height too:
	//ofRunApp(ske);
	/*
	NNodeObject *d = new NNodeObject(0,0,200,200,5,4);

	cout << d->getNumOfInput()<<endl<<d->getNumOfOutput() << endl 
		<<sizeof(NPoint)<<endl
		<<d->getInpt(0) << endl
		<< d->getInpt(1) << endl
		<< d->getInpt(2) << endl
		<< d->getInpt(3) << endl
		<< d->getInpt(4) << endl
		<< d->getOutpt(3) << endl
		<< endl <<endl;
	delete d;
	int c;
	cin >> c;*/
	return 0;
}
