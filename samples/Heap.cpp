template <class T>
class MyHeap {
  public:
    MyHeap(vector<T> input_arr, int max_length) :
        arr(input_arr), max_len(max_length), len(input_arr.size()) {
    }

    ~MyHeap() { arr.clear();}

    void Insert(T elem) {
        if (arr.size() > max_len) {
            Remove();
            return;
        }
        arr.emplace_back(elem);
        ++len;

        _Swim(len);

        // std::cout<< "After insert " << elem;
        // _Dump();
    }

    vector<T> Sort() {
        int temp_len = len;
        std::cout << "\n Before Sort \n";
        // _Dump();
        int p = 0;
        for (int i = len ; i >= 1; i--) {

            _Swap(1, i);
            len--;
            _Sink(1);

            // std::cout << "\n :  Phase " << ++p << " :  " << len;
            // _Dump();
        }
        len = temp_len;
        // std::cout << "\n After  Sort \n";
        // _Dump();
        _Verify();
        return arr;
    }

    T Get() {
        if (len == 0) {
            T temp;
            return temp;
        }

        return arr[0];
    }

    void Remove() {
        if (len == 0) {
            return;
        }
        _Swap(1, len);
        len--;
        _Sink(1);
        arr.pop_back();
    }

    int Size() const {
        return arr.size();
    }

  protected:

    void _Verify() {
        for (int i = 0 ;i < len-1;  i++) {
            if (arr[i] > arr[i+1]) {
                std::cout << "\nVerify failed";
            }
        }
        std::cout << "\nVerify Success";
    }

    void _Dump() const  {
        std::cout << "\n Dump = ";
        for (const auto& i : arr) {
            std::cout << "  , " << i;
        }
    }

    void _Build() {
        // std::vector<T> temp = arr;
        // arr.clear();
        // len = 0;
        // for (const T elem : temp) {
        //     Insert(elem);
        // }
        for (int i = len/2; i > 0 ; i--) {
            _Sink(i);
        }
    }
    int _GetPos(int i) {
        return i-1;
    }

    virtual void _Sink(int pos) = 0;

    virtual void _Swim(int pos) = 0;

    void _Swap(int x, int y) {
        int t = arr[_GetPos(x)];
        arr[_GetPos(x)] =  arr[_GetPos(y)];
        arr[_GetPos(y)] = t;
    }



    vector<T> arr;
    int len;
    int max_len;
};

template <class T>
class MaxHeap : public MyHeap<T> {
  public:
    MaxHeap(std::vector<T> input, int max_len) : MyHeap<T>(input, max_len) {
        MyHeap<T>::_Build();
    }

    virtual ~MaxHeap() {}

  protected:
    virtual void _Sink(int pos) override { // Top to down 
        // std::cout << "In Max heap sink pos = " << pos << "\n";
        int old_pos = pos;
        while (pos*2 <= MyHeap<T>::len) {
            int l = MyHeap<T>::arr[MyHeap<T>::_GetPos(pos*2)], r = 0;

            int max_pos = pos;
            if (pos*2 + 1 > MyHeap<T>::len) {
                max_pos = pos*2;
            }
            else {
                r = MyHeap<T>::arr[MyHeap<T>::_GetPos((pos*2)+1)];
                max_pos = l > r ? pos*2 : (pos*2)+1;
            }
            
            if (MyHeap<T>::arr[MyHeap<T>::_GetPos(pos)] < 
                    MyHeap<T>::arr[MyHeap<T>::_GetPos(max_pos)] ) {
                MyHeap<T>::_Swap(pos, max_pos);
            }
            pos = max_pos;
        }
    }

    virtual void _Swim(int pos) override {
        while (pos > 1 && MyHeap<T>::arr[MyHeap<T>::_GetPos(pos)] >
                              MyHeap<T>::arr[MyHeap<T>::_GetPos(pos/2)]) {
            MyHeap<T>::_Swap(pos, pos/2);
            pos/=2;
        }
    }
};


template <class T>
class MinHeap : public MyHeap<T> {
  public:
    MinHeap(std::vector<T> input, int max_length) : 
        MyHeap<T>(input, max_length) {
        MyHeap<T>::_Build();
    }


    virtual ~MinHeap() {}

  protected:
    virtual void _Sink(int pos) override { // Top to down 
        int old_pos = pos;
        while (pos*2 <= MyHeap<T>::len) {
            int l = MyHeap<T>::arr[MyHeap<T>::_GetPos(pos*2)], r = 0;

            int max_pos = pos;
            if (pos*2 + 1 > MyHeap<T>::len) {
                max_pos = pos*2;
            }
            else {
                r = MyHeap<T>::arr[MyHeap<T>::_GetPos((pos*2)+1)];
                max_pos = l < r ? pos*2 : (pos*2)+1;
            }
            
            if (MyHeap<T>::arr[MyHeap<T>::_GetPos(pos)] >
                  MyHeap<T>::arr[MyHeap<T>::_GetPos(max_pos)] ) {
                MyHeap<T>::_Swap(pos, max_pos);
            }
            pos = max_pos;
        }
    }

    virtual void _Swim(int pos) override {
        std::cout << " \n Swim  Compare pos " << pos;
        while (pos > 1 && MyHeap<T>::arr[MyHeap<T>::_GetPos(pos)] <
                            MyHeap<T>::arr[MyHeap<T>::_GetPos(pos/2)]) {
            std::cout << " \n Swim  swap pos " << pos << " and " << pos/2 <<endl;
            MyHeap<T>::_Swap(pos, pos/2);
            pos/=2;
        }
    }
};

class Solution {
public:
    int findKthLargest(vector<int>& nums, int k) {
        int len = nums.size();
        if (k > len) {
            return -1;
        }

        vector<int> temp;
        MinHeap<int> ob(temp, k);
        for (int i =0 ;i < nums.size(); i++) {
            if (ob.Size() < k) {
                ob.Insert(nums[i]);
            }
            else {
                if (ob.Get() < nums[i]) {
                    ob.Remove();
                    ob.Insert(nums[i]);
                }
            }
        }
        return ob.Get();}
};