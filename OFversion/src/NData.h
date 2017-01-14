#pragma once

struct Data_Base;
struct  NData{
	Data_Base *data;
};


enum {
	N2D_DATATYPE_NOTATYPE,
	N2D_DATATYPE_ARRAY,
	N2D_DATATYPE_INT,
	N2D_DATATYPE_FLOAT,
	N2D_DATATYPE_BOOL,
	N2D_DATATYPE_CHAR,
	N2D_DATATYPE_STRING,
	N2D_DATATYPE_IMAGE,
	N2D_DATATYPE_FUNCTION,
	N2D_DATATYPE_FILE,
	N2D_DATATYPE_WAVEFORM,
};
struct Data_Base {

};

struct Data_Array : Data_Base {
	void* ptr;
	int num[4] = { 0,-1,-1,-1 };//�ĸ�ά�ȵ�������Ϣ��������Ϊ-1
};

struct Data_Int : Data_Base {
	int* value;
};
struct Data_Float : Data_Base {
	int* value;//��ֵ
	char* tenToThe;//��ѧ���������10����
	char* visual;//������λ��Ч����
};
struct Data_Bool : Data_Base {
	bool* value;
};
struct Data_Char : Data_Base {
	char* value;
};
struct Data_String : Data_Char {};//Stringȫ����char[]������

struct Data_Image : Data_Base {//����δ��
	void* ptr;
};
struct Data_Function : Data_Base {//����δ��
	void* ptr;
};
struct Data_File : Data_Base {
	char* ptr;//�ļ�λ��
};
struct Data_Waveform : Data_File {
	char* ptr;//�ļ�λ��
};