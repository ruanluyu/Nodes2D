#pragma once

struct NLData {
	NLData* last;
	NLData* next;
	void* data;
};

class NList {
public:
	NList() ;
	~NList() ;
	void add(void* ) ;
	void clear();
	void* getCur();
	void* getNext();
	void goToStart() {
		cur = start;
	}
	void goToNext() {
		cur = cur->next;
	}
	void goToLast() {
		cur = cur->last;
	}
	int getSize() {
		return size;
	}
	void remove(void*);
private:
	NLData* start;
	NLData* end;
	NLData* cur;
	int size;
	void cutOut(NLData *ptr);
};